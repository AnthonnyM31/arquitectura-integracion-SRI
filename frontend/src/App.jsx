import { useState } from 'react'
import axios from 'axios'

function App() {
  // Estados del formulario
  const [ruc, setRuc] = useState('')
  const [email, setEmail] = useState('')
  const [placa, setPlaca] = useState('')
  
  // Estados de la aplicación
  const [datos, setDatos] = useState(null)
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState(null)

  // Función para consultar al Backend
  const consultar = async (e, buscarVehiculo = false) => {
    e.preventDefault()
    setLoading(true)
    setError(null)

    try {
      // Construimos la URL con parámetros query
      let url = `http://localhost:8080/api/integracion/consultar?ruc=${ruc}&email=${email}`
      
      // Si estamos buscando vehículo, agregamos la placa
      if (buscarVehiculo && placa) {
        url += `&placa=${placa}`
      }

      const response = await axios.get(url)
      setDatos(response.data)
    } catch (err) {
      console.error(err)
      setError("Error al consultar. Verifique el RUC o la conexión con el servidor.")
      setDatos(null)
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="min-h-screen bg-gray-100 py-10 px-4">
      <div className="max-w-3xl mx-auto">
        
        {/* --- CABECERA --- */}
        <div className="text-center mb-10">
          <h1 className="text-4xl font-bold text-blue-900">Sistema de Integración Gubernamental</h1>
          <p className="text-gray-600 mt-2">SRI • ANT • Registro Civil</p>
        </div>

        {/* --- TARJETA DE BÚSQUEDA --- */}
        <div className="bg-white rounded-lg shadow-xl p-8 mb-6">
          <form onSubmit={(e) => consultar(e, false)}>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              <div>
                <label className="block text-gray-700 font-bold mb-2">Correo Electrónico</label>
                <input 
                  type="email" 
                  className="w-full border border-gray-300 p-3 rounded focus:outline-none focus:border-blue-500"
                  placeholder="usuario@ejemplo.com"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  required
                />
              </div>
              <div>
                <label className="block text-gray-700 font-bold mb-2">RUC (Persona Natural)</label>
                <input 
                  type="text" 
                  className="w-full border border-gray-300 p-3 rounded focus:outline-none focus:border-blue-500"
                  placeholder="Ej: 1710012345001"
                  value={ruc}
                  onChange={(e) => setRuc(e.target.value)}
                  required
                />
              </div>
            </div>
            
            <button 
              type="submit"
              disabled={loading}
              className={`w-full mt-6 font-bold py-3 px-4 rounded transition duration-300 ${
                loading ? 'bg-gray-400 cursor-not-allowed' : 'bg-blue-600 hover:bg-blue-700 text-white'
              }`}
            >
              {loading ? 'Consultando Fuentes Externas...' : 'Verificar Contribuyente'}
            </button>
          </form>

          {error && (
            <div className="mt-4 p-4 bg-red-100 text-red-700 rounded border-l-4 border-red-500">
              {error}
            </div>
          )}
        </div>

        {/* --- RESULTADOS --- */}
        {datos && (
          <div className="space-y-6 animate-fade-in-up">
            
            {/* Datos Personales + ANT */}
            <div className="bg-white rounded-lg shadow-lg overflow-hidden border-t-4 border-green-500">
              <div className="bg-gray-50 p-4 border-b">
                <h2 className="text-xl font-bold text-gray-800">Información del Ciudadano</h2>
              </div>
              <div className="p-6 grid grid-cols-1 md:grid-cols-2 gap-4">
                <div>
                  <p className="text-sm text-gray-500">Razón Social / Nombre</p>
                  <p className="text-lg font-semibold">{datos.nombre}</p>
                </div>
                <div>
                  <p className="text-sm text-gray-500">Estado SRI</p>
                  <span className={`px-3 py-1 rounded-full text-sm font-bold ${
                    datos.estadoContribuyente === 'ACTIVO' ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'
                  }`}>
                    {datos.estadoContribuyente || 'DESCONOCIDO'}
                  </span>
                </div>
                <div>
                  <p className="text-sm text-gray-500">Puntos Licencia (ANT)</p>
                  <div className="flex items-center">
                    <span className="text-2xl font-bold text-orange-600">{datos.puntosLicencia}</span>
                    <span className="ml-2 text-xs text-gray-400">(Fuente: Cache/Scraping)</span>
                  </div>
                </div>
                <div>
                  <p className="text-sm text-gray-500">Email Registrado</p>
                  <p className="text-gray-800">{datos.email}</p>
                </div>
              </div>
            </div>

            {/* Sección Vehículo */}
            <div className="bg-white rounded-lg shadow-lg p-6 border-t-4 border-indigo-500">
              <h3 className="text-lg font-bold text-gray-800 mb-4">Consulta Vehicular</h3>
              <div className="flex gap-4">
                <input 
                  type="text" 
                  className="flex-1 border border-gray-300 p-3 rounded"
                  placeholder="Ingrese Placa (Ej: PABC1234)"
                  value={placa}
                  onChange={(e) => setPlaca(e.target.value)}
                />
                <button 
                  onClick={(e) => consultar(e, true)}
                  className="bg-indigo-600 hover:bg-indigo-700 text-white px-6 py-3 rounded font-bold"
                >
                  Buscar Vehículo
                </button>
              </div>

              {/* Lista de Vehículos */}
              {datos.vehiculos && datos.vehiculos.length > 0 ? (
                <div className="mt-6">
                  <table className="w-full text-left border-collapse">
                    <thead>
                      <tr className="bg-gray-100">
                        <th className="p-3 border-b">Placa</th>
                        <th className="p-3 border-b">Marca</th>
                        <th className="p-3 border-b">Modelo</th>
                        <th className="p-3 border-b">Año</th>
                      </tr>
                    </thead>
                    <tbody>
                      {datos.vehiculos.map((v, i) => (
                        <tr key={i} className="hover:bg-gray-50">
                          <td className="p-3 border-b font-mono font-bold">{v.placa}</td>
                          <td className="p-3 border-b">{v.marca}</td>
                          <td className="p-3 border-b">{v.modelo}</td>
                          <td className="p-3 border-b">{v.anio}</td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </div>
              ) : (
                <p className="mt-4 text-gray-500 italic text-center">
                  {placa ? "No se encontró información para esta placa." : "Ingrese una placa para consultar datos del vehículo."}
                </p>
              )}
            </div>

          </div>
        )}

      </div>
    </div>
  )
}

export default App