using System.Threading.Tasks;
using System.Windows;
using System.Windows.Threading;

namespace PeluPOS
{
    public partial class App : Application
    {
        protected override void OnStartup(StartupEventArgs e)
        {
            base.OnStartup(e);

            // Excepciones en el hilo de la UI (incluye async void)
            DispatcherUnhandledException += OnDispatcherUnhandledException;

            // Excepciones en Tasks no observadas (async/await sin try-catch)
            TaskScheduler.UnobservedTaskException += OnUnobservedTaskException;

            // Fallback para cualquier otro hilo
            AppDomain.CurrentDomain.UnhandledException += OnDomainUnhandledException;
        }

        private void OnDispatcherUnhandledException(object sender, DispatcherUnhandledExceptionEventArgs e)
        {
            MostrarError("Error de aplicación", e.Exception);
            e.Handled = true; // evita que la app cierre
        }

        private void OnUnobservedTaskException(object? sender, UnobservedTaskExceptionEventArgs e)
        {
            MostrarError("Error en tarea asíncrona", e.Exception.InnerException ?? e.Exception);
            e.SetObserved(); // evita que el proceso termine
        }

        private void OnDomainUnhandledException(object sender, UnhandledExceptionEventArgs e)
        {
            var ex = e.ExceptionObject as Exception;
            MostrarError("Error crítico", ex);
        }

        private static void MostrarError(string titulo, Exception? ex)
        {
            var mensaje = ex is System.Net.Http.HttpRequestException
                ? $"No se puede conectar con la API.\n\nAsegúrate de que el servidor está en marcha en:\nhttp://localhost/apiPeluPos/servicio/\n\nDetalle: {ex.Message}"
                : $"{ex?.GetType().Name}: {ex?.Message}";

            MessageBox.Show(mensaje, titulo, MessageBoxButton.OK, MessageBoxImage.Error);
        }
    }
}
