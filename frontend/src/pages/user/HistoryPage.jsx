import { useState, useEffect } from 'react'
import { Link } from 'react-router-dom'
import { getConsultationHistory } from '../../api/consultationAPI'
import Pagination from '../../components/common/Pagination'
import LoadingSpinner from '../../components/common/LoadingSpinner'
import EmptyState from '../../components/common/EmptyState'
import { FiClock, FiMessageCircle, FiPhone, FiVideo } from 'react-icons/fi'
import { format } from 'date-fns'

const HistoryPage = () => {
  const [history, setHistory] = useState([])
  const [loading, setLoading] = useState(true)
  const [page, setPage] = useState(1)
  const [totalPages, setTotalPages] = useState(1)
  const [filter, setFilter] = useState('all')

  useEffect(() => {
    fetchHistory()
  }, [page, filter])

  const fetchHistory = async () => {
    setLoading(true)
    try {
      const params = { page: page - 1, size: 10 }
      if (filter !== 'all') params.type = filter
      const { data } = await getConsultationHistory(params)
      setHistory(data.content || data || [])
      setTotalPages(data.totalPages || 1)
    } catch (err) {
      console.error('Failed to load history:', err)
    } finally {
      setLoading(false)
    }
  }

  const typeIcons = { chat: FiMessageCircle, call: FiPhone, video: FiVideo }

  return (
    <div className="page-transition">
      <h1 className="text-2xl font-bold text-gray-900 dark:text-white mb-6">Consultation History</h1>

      <div className="flex gap-2 mb-6">
        {['all', 'chat', 'call', 'video'].map((t) => (
          <button
            key={t}
            onClick={() => { setFilter(t); setPage(1) }}
            className={`px-4 py-2 rounded-xl text-sm font-medium capitalize transition-colors ${
              filter === t
                ? 'bg-primary text-white'
                : 'bg-gray-100 dark:bg-gray-800 text-gray-600 dark:text-gray-400 hover:bg-gray-200 dark:hover:bg-gray-700'
            }`}
          >
            {t}
          </button>
        ))}
      </div>

      {loading ? (
        <LoadingSpinner className="py-20" size="lg" />
      ) : history.length === 0 ? (
        <EmptyState icon={FiClock} title="No consultations found" description="Your consultation history will appear here" />
      ) : (
        <div className="bg-white dark:bg-gray-900 rounded-2xl border border-gray-100 dark:border-gray-800 divide-y divide-gray-100 dark:divide-gray-800">
          {history.map((c) => {
            const Icon = typeIcons[c.type] || FiMessageCircle
            return (
              <Link key={c.id} to={`/consultation/${c.id}`} className="flex items-center gap-4 p-4 hover:bg-gray-50 dark:hover:bg-gray-900/50 transition-colors">
                <div className="w-10 h-10 rounded-xl bg-primary/10 flex items-center justify-center">
                  <Icon className="text-primary" size={18} />
                </div>
                <div className="flex-1 min-w-0">
                  <p className="text-sm font-medium text-gray-900 dark:text-white truncate">{c.astrologerName}</p>
                  <div className="flex items-center gap-2 text-xs text-gray-400 mt-0.5">
                    <span>{format(new Date(c.startTime), 'MMM dd, yyyy HH:mm')}</span>
                    <span>•</span>
                    <span className="capitalize">{c.type}</span>
                    <span>•</span>
                    <span>{c.duration ? `${Math.floor(c.duration / 60)}m` : '-'}</span>
                  </div>
                </div>
                <div className="text-right">
                  <p className="text-sm font-semibold text-primary">₹{c.cost || 0}</p>
                  <p className={`text-xs ${c.status === 'completed' ? 'text-green-500' : 'text-gray-400'}`}>{c.status}</p>
                </div>
              </Link>
            )
          })}
        </div>
      )}
      <Pagination currentPage={page} totalPages={totalPages} onPageChange={setPage} />
    </div>
  )
}

export default HistoryPage
