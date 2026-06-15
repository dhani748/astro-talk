import dayjs from 'dayjs'
import { FiArrowUpRight, FiArrowDownLeft, FiPlus } from 'react-icons/fi'

const typeConfig = {
  CREDIT: { icon: FiArrowDownLeft, color: 'text-green-500', bg: 'bg-green-100 dark:bg-green-900/30' },
  DEBIT: { icon: FiArrowUpRight, color: 'text-red-500', bg: 'bg-red-100 dark:bg-red-900/30' },
  ADD_MONEY: { icon: FiPlus, color: 'text-green-500', bg: 'bg-green-100 dark:bg-green-900/30' },
  CONSULTATION_FEE: { icon: FiArrowUpRight, color: 'text-red-500', bg: 'bg-red-100 dark:bg-red-900/30' },
  REFUND: { icon: FiArrowDownLeft, color: 'text-green-500', bg: 'bg-green-100 dark:bg-green-900/30' },
}

const TransactionRow = ({ transaction }) => {
  const config = typeConfig[transaction.type] || typeConfig.DEBIT
  const Icon = config.icon

  return (
    <div className="flex items-center gap-4 py-3 px-4 hover:bg-white/5 transition-colors rounded-xl">
      <div className={`w-10 h-10 rounded-xl ${config.bg} flex items-center justify-center`}>
        <Icon className={config.color} size={18} />
      </div>
      <div className="flex-1 min-w-0">
        <p className="text-sm font-medium text-light truncate">{transaction.description}</p>
        <p className="text-xs text-muted">{dayjs(transaction.createdAt).format('MMM dd, yyyy HH:mm')}</p>
      </div>
      <span className={`text-sm font-semibold ${transaction.amount >= 0 ? 'text-green-500' : 'text-red-500'}`}>
        {transaction.amount >= 0 ? '+' : ''}₹{Math.abs(transaction.amount).toLocaleString()}
      </span>
    </div>
  )
}

export default TransactionRow
