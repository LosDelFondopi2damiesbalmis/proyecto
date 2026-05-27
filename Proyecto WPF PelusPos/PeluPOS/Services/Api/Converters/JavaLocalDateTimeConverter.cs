using System.Globalization;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace PeluPOS.Services.Api.Converters
{
    /// <summary>
    /// Serializes DateTime as "yyyy-MM-ddTHH:mm:ss" to match the Java API
    /// @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss") annotation on LocalDateTime fields.
    /// Without this converter, System.Text.Json produces fractional seconds
    /// (e.g. "2026-05-27T16:24:08.1234567") which Java's JSON-B cannot parse.
    /// </summary>
    public class JavaLocalDateTimeConverter : JsonConverter<DateTime>
    {
        private const string WriteFormat = "yyyy-MM-ddTHH:mm:ss";

        public override DateTime Read(ref Utf8JsonReader reader, Type typeToConvert, JsonSerializerOptions options)
        {
            var str = reader.GetString() ?? string.Empty;
            if (DateTime.TryParse(str, CultureInfo.InvariantCulture, DateTimeStyles.None, out var dt))
                return dt;
            return DateTime.MinValue;
        }

        public override void Write(Utf8JsonWriter writer, DateTime value, JsonSerializerOptions options)
        {
            writer.WriteStringValue(value.ToString(WriteFormat, CultureInfo.InvariantCulture));
        }
    }
}
