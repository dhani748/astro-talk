import { useState, useEffect } from 'react'
import { getRevenueReport } from '../../api/adminAPI'
import { FiDollarSign, FiCalendar } from 'react-icons/fi'
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from 'recharts'
import LoadingSpinner from '../../components/common/LoadingSpinner'

const AdminRevenuePage = () => {
  const [revenue, setRevenue] = useState([])
  const [loading, setLoading] = useState(true)
  const [range, setRange] = useState('weekly')
  const [total, setTotal] = useState(0)

  useEffect(() => {
    setLoading(true)
    getRevenueReport({ range })
      .then(({ data }) => {
        setRevenue(data.chart || data || [])
        setTotal(data.total || 0)
      })
      .catch(() => {})
      .finally(() => setLoading(false))
  }, [range])

  return (
    <div className="page-transition">
      <div className="flex items-center justify-between mb-6">
        <div>
          <h1 className="text-2xl font-bold text-gray-900 dark:text-white">Revenue</h1>
          <p className="text-gray-500 dark:text-gray-400 mt-1">Track platform earnings</p>
        </div>
        <div className="text-right">
          <p className="text-xs text-gray-400">Total Revenue</p>
          <p className="text-2xl font-bold text-green-500">₹{total.toLocaleString()}</p>
        </div>
      </div>

      <div className="flex gap-2 mb-6">
        {['daily', 'weekly', 'monthly', 'yearly'].map((r) => (
          <button key={r} onClick={() => setRange(r)}
            className={`px-4 py-2 rounded-xl text-sm font-medium capitalize transition-colors ${
              range === r ? 'bg-primary text-white' : 'bg-gray-100 dark:bg-gray-800 text-gray-600 dark:text-gray-400 hover:bg-gray-200 dark:hover:bg-gray-700'
            }`}>
            {r}
          </button>
        ))}
      </div>

      {loading ? (
        <LoadingSpinner className="py-20" size="lg" />
      ) : (
        <div className="bg-white dark:bg-gray-900 rounded-2xl p-6 border border-gray-100 dark:border-gray-800">
          <div className="h-80">
            <ResponsiveContainer width="100%" height="100%">
              <BarChart data={revenue}>
                <CartesianGrid strokeDasharray="3 3" stroke="#e5e7eb" />
                <XAxis dataKey="name" tick={{ fill: '#9ca3af', fontSize: 12 }} />
                <YAxis tick={{ fill: '#9ca3af', fontSize: 12 }} />
                <Tooltip
                  contentStyle={{ background: '#fff', border: '1px solid #e5e7eb', borderRadius: '12px' }}
                  labelStyle={{ color: '#111827' }}
                />
                <Bar dataKey="revenue" fill="#6B21A8" radius={[8, 8, 0, 0]} />
              </BarChart>
            </ResponsiveContainer>
          </div>
        </div>
      )}
    </div>
  )
}

export default AdminRevenuePage
