import { useState, useEffect } from 'react'
import { getUsers } from '../../api/adminAPI'
import Pagination from '../../components/common/Pagination'
import LoadingSpinner from '../../components/common/LoadingSpinner'
import EmptyState from '../../components/common/EmptyState'
import { FiUsers, FiSearch } from 'react-icons/fi'

const AdminUsersPage = () => {
  const [users, setUsers] = useState([])
  const [loading, setLoading] = useState(true)
  const [page, setPage] = useState(1)
  const [totalPages, setTotalPages] = useState(1)
  const [search, setSearch] = useState('')

  useEffect(() => {
    setLoading(true)
    getUsers({ page: page - 1, size: 10, search })
      .then(({ data }) => {
        setUsers(data.content || data || [])
        setTotalPages(data.totalPages || 1)
      })
      .catch(() => {})
      .finally(() => setLoading(false))
  }, [page, search])

  return (
    <div className="page-transition">
      <div className="flex items-center justify-between mb-6">
        <h1 className="text-2xl font-bold text-gray-900 dark:text-white">Users</h1>
        <div className="relative">
          <FiSearch className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400" size={16} />
          <input
            type="text" placeholder="Search users..."
            value={search} onChange={(e) => { setSearch(e.target.value); setPage(1) }}
            className="pl-10 pr-4 py-2 rounded-xl border border-gray-200 dark:border-gray-700 bg-gray-50 dark:bg-gray-800 text-gray-900 dark:text-white text-sm focus:outline-none focus:ring-2 focus:ring-primary/30 w-64"
          />
        </div>
      </div>

      {loading ? (
        <LoadingSpinner className="py-20" size="lg" />
      ) : users.length === 0 ? (
        <EmptyState icon={FiUsers} title="No users found" />
      ) : (
        <div className="bg-white dark:bg-gray-900 rounded-2xl border border-gray-100 dark:border-gray-800 overflow-hidden">
          <table className="w-full">
            <thead>
              <tr className="border-b border-gray-100 dark:border-gray-800">
                <th className="text-left p-4 text-sm font-medium text-gray-500 dark:text-gray-400">Name</th>
                <th className="text-left p-4 text-sm font-medium text-gray-500 dark:text-gray-400">Email</th>
                <th className="text-left p-4 text-sm font-medium text-gray-500 dark:text-gray-400">Phone</th>
                <th className="text-left p-4 text-sm font-medium text-gray-500 dark:text-gray-400">Role</th>
                <th className="text-left p-4 text-sm font-medium text-gray-500 dark:text-gray-400">Joined</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-gray-100 dark:divide-gray-800">
              {users.map((u) => (
                <tr key={u.id} className="hover:bg-gray-50 dark:hover:bg-gray-900/50 transition-colors">
                  <td className="p-4">
                    <div className="flex items-center gap-3">
                      <img src={u.profilePhoto || `https://ui-avatars.com/api/?name=${u.name}&background=6B21A8&color=fff&size=32`} alt="" className="w-8 h-8 rounded-lg object-cover" />
                      <span className="text-sm font-medium text-gray-900 dark:text-white">{u.name}</span>
                    </div>
                  </td>
                  <td className="p-4 text-sm text-gray-600 dark:text-gray-400">{u.email}</td>
                  <td className="p-4 text-sm text-gray-600 dark:text-gray-400">{u.phone || '-'}</td>
                  <td className="p-4">
                    <span className={`px-2 py-1 rounded-lg text-xs font-medium capitalize ${
                      u.role === 'admin' ? 'bg-red-100 text-red-700 dark:bg-red-900/30 dark:text-red-400'
                        : u.role === 'astro' ? 'bg-purple-100 text-purple-700 dark:bg-purple-900/30 dark:text-purple-400'
                        : 'bg-blue-100 text-blue-700 dark:bg-blue-900/30 dark:text-blue-400'
                    }`}>
                      {u.role}
                    </span>
                  </td>
                  <td className="p-4 text-sm text-gray-600 dark:text-gray-400">{u.createdAt ? new Date(u.createdAt).toLocaleDateString() : '-'}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
      <Pagination currentPage={page} totalPages={totalPages} onPageChange={setPage} />
    </div>
  )
}

export default AdminUsersPage
