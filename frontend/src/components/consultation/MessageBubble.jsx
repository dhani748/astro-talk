import { format } from 'date-fns'
import { useSelector } from 'react-redux'
import { FiCheck, FiCheckCircle } from 'react-icons/fi'

const MessageBubble = ({ message }) => {
  const { user } = useSelector((state) => state.auth)
  const isOwn = message.senderId === user?.id

  return (
    <div className={`flex ${isOwn ? 'justify-end' : 'justify-start'}`}>
      <div
        className={`max-w-[75%] px-4 py-2.5 rounded-2xl ${
          isOwn
            ? 'bg-primary text-white rounded-br-sm'
            : 'bg-gray-100 dark:bg-gray-800 text-gray-900 dark:text-white rounded-bl-sm'
        }`}
      >
        <p className="text-sm leading-relaxed">{message.content}</p>
        <div className={`flex items-center gap-1 mt-1 ${isOwn ? 'justify-end' : 'justify-start'}`}>
          <span className="text-[10px] opacity-70">
            {format(new Date(message.timestamp || new Date()), 'HH:mm')}
          </span>
          {isOwn && (
            message.read ? <FiCheckCircle size={12} className="opacity-70" /> : <FiCheck size={12} className="opacity-70" />
          )}
        </div>
      </div>
    </div>
  )
}

export default MessageBubble
