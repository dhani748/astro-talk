const statusStyles = {
  online: 'bg-green-100 text-green-700 dark:bg-green-900/30 dark:text-green-400',
  offline: 'bg-gray-100 text-gray-600 dark:bg-gray-800 dark:text-gray-400',
  busy: 'bg-red-100 text-red-700 dark:bg-red-900/30 dark:text-red-400',
  pending: 'bg-yellow-100 text-yellow-700 dark:bg-yellow-900/30 dark:text-yellow-400',
  verified: 'bg-green-100 text-green-700 dark:bg-green-900/30 dark:text-green-400',
}

const Badge = ({ status, children }) => {
  const style = statusStyles[status] || statusStyles.offline

  return (
    <span className={`inline-flex items-center gap-1.5 px-2.5 py-1 rounded-full text-xs font-medium ${style}`}>
      <span className={`w-1.5 h-1.5 rounded-full ${status === 'online' ? 'bg-green-500 animate-pulse' : status === 'busy' ? 'bg-red-500' : 'bg-current'}`} />
      {children || status}
    </span>
  )
}

export default Badge
