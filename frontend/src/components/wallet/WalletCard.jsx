import { FiPlus } from 'react-icons/fi'

const WalletCard = ({ balance, onAddMoney }) => {
  return (
    <div className="bg-gradient-to-br from-primary via-primary-dark to-purple-900 rounded-2xl p-6 text-white shadow-lg">
      <p className="text-sm text-purple-200 mb-1">Wallet Balance</p>
      <h2 className="text-4xl font-bold mb-4">₹{balance?.toLocaleString() || '0'}</h2>
      <button
        onClick={onAddMoney}
        className="flex items-center gap-2 px-4 py-2 bg-gold text-gray-900 rounded-xl font-semibold hover:bg-gold-dark transition-colors"
      >
        <FiPlus size={18} /> Add Money
      </button>
    </div>
  )
}

export default WalletCard
