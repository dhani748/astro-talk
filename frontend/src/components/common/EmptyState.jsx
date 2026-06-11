import { FiInbox } from 'react-icons/fi'

const EmptyState = ({ icon: Icon = FiInbox, title = 'Nothing here', description = '', action }) => {
  return (
    <div className="flex flex-col items-center justify-center py-16 px-4">
      <Icon className="text-gray-300 dark:text-gray-600 text-6xl mb-4" />
      <h3 className="text-lg font-semibold text-gray-600 dark:text-gray-400 mb-2">{title}</h3>
      {description && (
        <p className="text-sm text-gray-400 dark:text-gray-500 text-center max-w-sm mb-4">
          {description}
        </p>
      )}
      {action}
    </div>
  )
}

export default EmptyState
