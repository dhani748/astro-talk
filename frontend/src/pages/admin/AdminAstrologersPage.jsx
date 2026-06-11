import { useState, useEffect } from 'react'
import { getPendingAstrologers, verifyAstrologer } from '../../api/adminAPI'
import { FiStar, FiCheck, FiX } from 'react-icons/fi'
import LoadingSpinner from '../../components/common/LoadingSpinner'
import EmptyState from '../../components/common/EmptyState'
import toast from 'react-hot-toast'

const AdminAstrologersPage = () => {
  const [astrologers, setAstrologers] = useState([])
  const [loading, setLoading] = useState(true)
  const [actionLoading, setActionLoading] = useState(null)

  useEffect(() => { fetchPending() }, [])

  const fetchPending = async () => {
    try {
      const { data } = await getPendingAstrologers()
      setAstrologers(data.content || data || [])
    } catch {
      toast.error('Failed to load')
    } finally {
      setLoading(false)
    }
  }

  const handleVerify = async (id, status) => {
    setActionLoading(id)
    try {
      await verifyAstrologer(id, status)
      toast.success(`Astrologer ${status === 'verified' ? 'verified' : 'rejected'}`)
      setAstrologers((prev) => prev.filter((a) => a.id !== id))
    } catch {
      toast.error('Failed to update')
    } finally {
      setActionLoading(null)
    }
  }

  if (loading) return <LoadingSpinner className="py-20" size="lg" />

  return (
    <div className="page-transition">
      <h1 className="text-2xl font-bold text-gray-900 dark:text-white mb-6">Pending Verifications</h1>

      {astrologers.length === 0 ? (
        <EmptyState icon={FiStar} title="No pending verifications" description="All astrologers have been reviewed" />
      ) : (
        <div className="space-y-4">
          {astrologers.map((a) => (
            <div key={a.id} className="bg-white dark:bg-gray-900 rounded-2xl p-6 border border-gray-100 dark:border-gray-800">
              <div className="flex items-start gap-4">
                <img src={a.profilePhoto || `https://ui-avatars.com/api/?name=${a.name}&background=6B21A8&color=fff&size=56`} alt="" className="w-14 h-14 rounded-xl object-cover" />
                <div className="flex-1">
                  <h3 className="font-semibold text-gray-900 dark:text-white">{a.name}</h3>
                  <p className="text-sm text-gray-500 dark:text-gray-400">{a.specialization}</p>
                  <p className="text-sm text-gray-400 mt-1">{a.email} | {a.phone}</p>
                  <div className="flex items-center gap-2 mt-2">
                    <span className="text-xs bg-yellow-100 dark:bg-yellow-900/30 text-yellow-700 dark:text-yellow-400 px-2 py-0.5 rounded-full">Pending</span>
                    <span className="text-xs text-gray-400">Experience: {a.experience || 'NA'} yrs</span>
                  </div>
                  {a.bio && <p className="text-sm text-gray-600 dark:text-gray-400 mt-2 line-clamp-2">{a.bio}</p>}
                </div>
                <div className="flex gap-2">
                  <button onClick={() => handleVerify(a.id, 'verified')} disabled={actionLoading === a.id}
                    className="flex items-center gap-1 px-3 py-1.5 bg-green-500 text-white rounded-lg text-sm font-medium hover:bg-green-600 transition-colors disabled:opacity-50">
                    <FiCheck size={14} /> Verify
                  </button>
                  <button onClick={() => handleVerify(a.id, 'rejected')} disabled={actionLoading === a.id}
                    className="flex items-center gap-1 px-3 py-1.5 bg-red-500 text-white rounded-lg text-sm font-medium hover:bg-red-600 transition-colors disabled:opacity-50">
                    <FiX size={14} /> Reject
                  </button>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  )
}

export default AdminAstrologersPage
