import { useState, useEffect } from 'react'
import { useSelector, useDispatch } from 'react-redux'
import { updateProfile } from '../../../api/authAPI'
import { updateUser } from '../store/authSlice'
import LoadingSpinner from '../../../components/common/LoadingSpinner'
import { FiUser, FiMail, FiPhone, FiCalendar, FiMapPin } from 'react-icons/fi'
import toast from 'react-hot-toast'

const ProfilePage = () => {
  const dispatch = useDispatch()
  const { user } = useSelector((state) => state.auth)
  const [form, setForm] = useState({
    name: '', email: '', phone: '', dob: '', gender: '', address: '', city: '', state: '',
  })
  const [loading, setLoading] = useState(false)
  const [saving, setSaving] = useState(false)

  useEffect(() => {
    if (user) {
      setForm({
        name: user.name || '',
        email: user.email || '',
        phone: user.phone || '',
        dob: user.dob || '',
        gender: user.gender || '',
        address: user.address || '',
        city: user.city || '',
        state: user.state || '',
      })
    }
    setLoading(false)
  }, [user])

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value })

  const handleSubmit = async (e) => {
    e.preventDefault()
    setSaving(true)
    try {
      const { data } = await updateProfile(form)
      dispatch(updateUser(data))
      toast.success('Profile updated successfully')
    } catch (err) {
      toast.error(err.response?.data?.message || 'Failed to update profile')
    } finally {
      setSaving(false)
    }
  }

  if (loading) return <LoadingSpinner className="py-20" size="lg" />

  return (
    <div className="max-w-2xl page-transition">
      <h1 className="text-2xl font-bold text-light mb-6">Profile</h1>

      <div className="bg-cosmic-2 rounded-2xl border border-white/5 p-8">
        <div className="flex items-center gap-4 mb-8">
          <img
            src={user?.profilePhoto || `https://ui-avatars.com/api/?name=${user?.name || 'U'}&background=6B21A8&color=fff&size=80`}
            alt="Profile"
            className="w-20 h-20 rounded-2xl object-cover"
          />
          <div>
            <h2 className="text-xl font-semibold text-light">{user?.name}</h2>
            <p className="text-sm text-muted">{user?.email}</p>
          </div>
        </div>

        <form onSubmit={handleSubmit} className="space-y-4">
          <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
            <div>
              <label className="block text-sm font-medium text-light mb-1.5"><FiUser className="inline mr-1" size={14} /> Full Name</label>
              <input type="text" name="name" value={form.name} onChange={handleChange} className="w-full px-4 py-2.5 rounded-xl border border-white/10 bg-cosmic-3 text-light text-sm focus:outline-none focus:ring-2 focus:ring-gold/30" />
            </div>
            <div>
              <label className="block text-sm font-medium text-light mb-1.5"><FiMail className="inline mr-1" size={14} /> Email</label>
              <input type="email" name="email" value={form.email} onChange={handleChange} className="w-full px-4 py-2.5 rounded-xl border border-white/10 bg-cosmic-3 text-light text-sm focus:outline-none focus:ring-2 focus:ring-gold/30" />
            </div>
            <div>
              <label className="block text-sm font-medium text-light mb-1.5"><FiPhone className="inline mr-1" size={14} /> Phone</label>
              <input type="tel" name="phone" value={form.phone} onChange={handleChange} className="w-full px-4 py-2.5 rounded-xl border border-white/10 bg-cosmic-3 text-light text-sm focus:outline-none focus:ring-2 focus:ring-gold/30" />
            </div>
            <div>
              <label className="block text-sm font-medium text-light mb-1.5"><FiCalendar className="inline mr-1" size={14} /> Date of Birth</label>
              <input type="date" name="dob" value={form.dob} onChange={handleChange} className="w-full px-4 py-2.5 rounded-xl border border-white/10 bg-cosmic-3 text-light text-sm focus:outline-none focus:ring-2 focus:ring-gold/30" />
            </div>
            <div>
              <label className="block text-sm font-medium text-light mb-1.5">Gender</label>
              <select name="gender" value={form.gender} onChange={handleChange} className="w-full px-4 py-2.5 rounded-xl border border-white/10 bg-cosmic-3 text-light text-sm focus:outline-none focus:ring-2 focus:ring-gold/30">
                <option value="">Select</option>
                <option value="male">Male</option>
                <option value="female">Female</option>
                <option value="other">Other</option>
              </select>
            </div>
            <div>
              <label className="block text-sm font-medium text-light mb-1.5"><FiMapPin className="inline mr-1" size={14} /> City</label>
              <input type="text" name="city" value={form.city} onChange={handleChange} className="w-full px-4 py-2.5 rounded-xl border border-white/10 bg-cosmic-3 text-light text-sm focus:outline-none focus:ring-2 focus:ring-gold/30" />
            </div>
          </div>
          <div>
            <label className="block text-sm font-medium text-light mb-1.5">Address</label>
            <textarea name="address" value={form.address} onChange={handleChange} rows={2} className="w-full px-4 py-2.5 rounded-xl border border-white/10 bg-cosmic-3 text-light text-sm focus:outline-none focus:ring-2 focus:ring-gold/30" />
          </div>

          <button type="submit" disabled={saving} className="px-6 py-2.5 bg-gold text-cosmic rounded-xl font-medium hover:opacity-90 transition-colors disabled:opacity-50">
            {saving ? 'Saving...' : 'Save Changes'}
          </button>
        </form>
      </div>
    </div>
  )
}

export default ProfilePage
