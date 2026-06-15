import { Link } from 'react-router-dom'
import { FiAlertTriangle } from 'react-icons/fi'

const BalanceWarning = ({ balance }) => {
  if (balance > 50) return null

  return (
    <div className="flex items-center gap-3 p-4 bg-red-50 dark:bg-red-900/20 border border-red-200 dark:border-red-800 rounded-xl">
      <FiAlertTriangle className="text-red-500 flex-shrink-0" size={20} />
      <div className="flex-1">
        <p className="text-sm font-medium text-red-700 dark:text-red-400">Low Balance</p>
        <p className="text-xs text-red-600 dark:text-red-500">Your wallet balance is ₹{balance}. Add money to continue.</p>
      </div>
      <Link to="/wallet" className="px-3 py-1.5 bg-red-500 text-white rounded-lg text-sm font-medium hover:bg-red-600 transition-colors">
        Add Money
      </Link>
    </div>
  )
}

export default BalanceWarning
