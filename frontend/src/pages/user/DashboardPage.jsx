import { useState, useEffect } from 'react'
import { Link } from 'react-router-dom'
import { useSelector, useDispatch } from 'react-redux'
import { getWalletBalance } from '../../api/walletAPI'
import { getConsultationHistory } from '../../api/consultationAPI'
import { setBalance } from '../../store/walletSlice'
import { FiCreditCard, FiClock, FiStar, FiArrowRight, FiMessageCircle, FiPhone } from 'react-icons/fi'
import WalletCard from '../../components/wallet/WalletCard'
import LoadingSpinner from '../../components/common/LoadingSpinner'
import EmptyState from '../../components/common/EmptyState'
import { format } from 'date-fns'

const DashboardPage = () => {
  const dispatch = useDispatch()
  const { user } = useSelector((state) => state.auth)
  const { balance } = useSelector((state) => state.wallet)
  const [history, setHistory] = useState([])
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    Promise.all([
      getWalletBalance().then(({ data }) => dispatch(setBalance(data.balance))).catch(() => {}),
      getConsultationHistory({ page: 0, size: 5 }).then(({ data }) => setHistory(data.content || data || [])).catch(() => {}),
    ]).finally(() => setLoading(false))
  }, [dispatch])

  if (loading) return <LoadingSpinner className="py-20" size="lg" />

  return (
    <div className="page-transition">
      <div className="mb-8">
        <h1 className="text-2xl font-bold text-gray-900 dark:text-white">Welcome, {user?.name}!</h1>
        <p className="text-gray-500 dark:text-gray-400">Here's your astrology journey</p>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6 mb-8">
        <Link to="/wallet" className="lg:col-span-1">
          <WalletCard balance={balance} />
        </Link>

        <div className="lg:col-span-2 grid grid-cols-2 sm:grid-cols-3 gap-4">
          {[
            { icon: FiClock, label: 'Consultations', value: history.length, color: 'text-blue-500', bg: 'bg-blue-100 dark:bg-blue-900/30' },
            { icon: FiStar, label: 'Favourites', value: '0', color: 'text-gold', bg: 'bg-amber-100 dark:bg-amber-900/30' },
              { icon: FiCreditCard, label: 'Total Spent', value: `₹${history.reduce((s, h) => s + (h.cost || 0), 0)}`, color: 'text-green-500', bg: 'bg-green-100 dark:bg-green-900/30' },
          ].map((stat, i) => (
            <div key={i} className="bg-white dark:bg-gray-900 rounded-2xl p-5 border border-gray-100 dark:border-gray-800">
              <div className={`w-10 h-10 rounded-xl ${stat.bg} flex items-center justify-center mb-3`}>
                <stat.icon className={stat.color} size={20} />
              </div>
              <p className="text-xs text-gray-500 dark:text-gray-400">{stat.label}</p>
              <p className="text-lg font-bold text-gray-900 dark:text-white mt-0.5">{stat.value}</p>
            </div>
          ))}
        </div>
      </div>

      <div className="bg-white dark:bg-gray-900 rounded-2xl border border-gray-100 dark:border-gray-800">
        <div className="flex items-center justify-between p-6 border-b border-gray-100 dark:border-gray-800">
          <h2 className="font-semibold text-gray-900 dark:text-white">Recent Consultations</h2>
          <Link to="/history" className="text-sm text-primary font-medium hover:underline flex items-center gap-1">
            View All <FiArrowRight size={14} />
          </Link>
        </div>
        {history.length === 0 ? (
          <div className="p-6">
            <EmptyState icon={FiClock} title="No consultations yet" description="Start your first consultation with an expert astrologer" action={
              <Link to="/astrologers" className="inline-flex items-center gap-2 px-4 py-2 bg-primary text-white rounded-xl text-sm font-medium hover:bg-primary-dark transition-colors">
                Find Astrologers
              </Link>
            } />
          </div>
        ) : (
          <div className="divide-y divide-gray-100 dark:divide-gray-800">
            {history.map((consultation) => (
              <Link key={consultation.id} to={`/consultation/${consultation.id}`} className="flex items-center gap-4 p-4 hover:bg-gray-50 dark:hover:bg-gray-900/50 transition-colors">
                <div className="w-10 h-10 rounded-xl bg-primary/10 flex items-center justify-center">
                  {consultation.type === 'call' ? <FiPhone className="text-primary" size={18} /> : <FiMessageCircle className="text-primary" size={18} />}
                </div>
                <div className="flex-1 min-w-0">
                  <p className="text-sm font-medium text-gray-900 dark:text-white truncate">{consultation.astrologerName}</p>
                  <p className="text-xs text-gray-400">{format(new Date(consultation.startTime), 'MMM dd, yyyy HH:mm')}</p>
                </div>
                <span className="text-sm font-medium text-primary">₹{consultation.cost || 0}</span>
              </Link>
            ))}
          </div>
        )}
      </div>
    </div>
  )
}

export default DashboardPage
