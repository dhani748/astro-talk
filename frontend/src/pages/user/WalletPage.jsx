import { useState, useEffect, useCallback } from 'react'
import { useSelector, useDispatch } from 'react-redux'
import { getWalletBalance, getTransactions } from '../../api/walletAPI'
import { setBalance, setTransactions } from '../../store/walletSlice'
import WalletCard from '../../components/wallet/WalletCard'
import TransactionRow from '../../components/wallet/TransactionRow'
import RazorpayButton from '../../components/wallet/RazorpayButton'
import Modal from '../../components/common/Modal'
import LoadingSpinner from '../../components/common/LoadingSpinner'
import EmptyState from '../../components/common/EmptyState'
import Pagination from '../../components/common/Pagination'
import { FiRefreshCw, FiList } from 'react-icons/fi'

const WalletPage = () => {
  const dispatch = useDispatch()
  const { balance, transactions } = useSelector((state) => state.wallet)
  const [loading, setLoading] = useState(true)
  const [showAddModal, setShowAddModal] = useState(false)
  const [amount, setAmount] = useState(100)
  const [page, setPage] = useState(0)
  const [totalPages, setTotalPages] = useState(1)

  const fetchData = useCallback(async () => {
    try {
      const [balanceRes, txRes] = await Promise.all([
        getWalletBalance(),
        getTransactions({ page, size: 10 }),
      ])
      dispatch(setBalance(balanceRes.data.balance))
      dispatch(setTransactions(txRes.data.content || txRes.data || []))
      setTotalPages(txRes.data.totalPages || 1)
    } catch (err) {
      console.error('Failed to load wallet data:', err)
    } finally {
      setLoading(false)
    }
  }, [dispatch, page])

  useEffect(() => { fetchData() }, [fetchData])

  if (loading) return <LoadingSpinner className="py-20" size="lg" />

  return (
    <div className="page-transition">
      <h1 className="text-2xl font-bold text-gray-900 dark:text-white mb-6">Wallet</h1>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6 mb-8">
        <div className="lg:col-span-1">
          <WalletCard balance={balance} onAddMoney={() => setShowAddModal(true)} />
        </div>
        <div className="lg:col-span-2">
          <div className="bg-white dark:bg-gray-900 rounded-2xl border border-gray-100 dark:border-gray-800">
            <div className="flex items-center justify-between p-6 border-b border-gray-100 dark:border-gray-800">
              <h2 className="font-semibold text-gray-900 dark:text-white">Quick Add</h2>
            </div>
            <div className="p-6">
              <div className="flex flex-wrap gap-3 mb-4">
                {[100, 200, 500, 1000].map((val) => (
                  <button
                    key={val}
                    onClick={() => setAmount(val)}
                    className={`px-4 py-2 rounded-xl text-sm font-medium border transition-colors ${
                      amount === val
                        ? 'border-primary bg-primary/10 text-primary'
                        : 'border-gray-200 dark:border-gray-700 text-gray-600 dark:text-gray-400 hover:border-primary'
                    }`}
                  >
                    ₹{val}
                  </button>
                ))}
              </div>
              <div className="flex items-center gap-3">
                <input
                  type="number"
                  value={amount}
                  onChange={(e) => setAmount(Number(e.target.value))}
                  min={10}
                  className="flex-1 px-4 py-2.5 rounded-xl border border-gray-200 dark:border-gray-700 bg-gray-50 dark:bg-gray-800 text-gray-900 dark:text-white text-sm focus:outline-none focus:ring-2 focus:ring-primary/30"
                />
                <RazorpayButton amount={amount} onSuccess={() => { setShowAddModal(false); fetchData() }} />
              </div>
            </div>
          </div>
        </div>
      </div>

      <div className="bg-white dark:bg-gray-900 rounded-2xl border border-gray-100 dark:border-gray-800">
        <div className="flex items-center justify-between p-6 border-b border-gray-100 dark:border-gray-800">
          <h2 className="font-semibold text-gray-900 dark:text-white">Transaction History</h2>
          <button onClick={fetchData} className="p-2 hover:bg-gray-100 dark:hover:bg-gray-800 rounded-lg transition-colors">
            <FiRefreshCw className="text-gray-400" size={16} />
          </button>
        </div>
        {transactions.length === 0 ? (
          <div className="p-6">
            <EmptyState icon={FiList} title="No transactions" description="Your transactions will appear here" />
          </div>
        ) : (
          <div className="divide-y divide-gray-100 dark:divide-gray-800">
            {transactions.map((tx) => (
              <TransactionRow key={tx.id} transaction={tx} />
            ))}
          </div>
        )}
        <div className="p-4 border-t border-gray-100 dark:border-gray-800">
          <Pagination currentPage={page + 1} totalPages={totalPages} onPageChange={(p) => setPage(p - 1)} />
        </div>
      </div>
    </div>
  )
}

export default WalletPage
