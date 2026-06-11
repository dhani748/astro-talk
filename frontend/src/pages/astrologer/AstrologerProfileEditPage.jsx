import { useState, useEffect } from 'react'
import { useSelector } from 'react-redux'
import { updateAstrologerProfile, getAstrologerProfile } from '../../api/astrologerAPI'
import LoadingSpinner from '../../components/common/LoadingSpinner'
import toast from 'react-hot-toast'

const specializations = ['Vedic', 'Numerology', 'Palmistry', 'Tarot', 'Vastu', 'Astrology', 'Face Reading']
const languages = ['Hindi', 'English', 'Tamil', 'Telugu', 'Bengali', 'Marathi', 'Gujarati']

const AstrologerProfileEditPage = () => {
  const { user } = useSelector((state) => state.auth)
  const [form, setForm] = useState({
    bio: '', experience: 1, pricePerMin: 10,
    specializations: [], languages: [], profilePhoto: '',
  })
  const [loading, setLoading] = useState(true)
  const [saving, setSaving] = useState(false)

  useEffect(() => {
    getAstrologerProfile(user?.id)
      .then(({ data }) => {
        setForm({
          bio: data.bio || '',
          experience: data.experience || 1,
          pricePerMin: data.pricePerMin || 10,
          specializations: data.specializations || [],
          languages: data.languages || ['Hindi', 'English'],
          profilePhoto: data.profilePhoto || '',
        })
      })
      .catch(() => {})
      .finally(() => setLoading(false))
  }, [user?.id])

  const toggleArray = (arr, item) =>
    arr.includes(item) ? arr.filter((a) => a !== item) : [...arr, item]

  const handleSubmit = async (e) => {
    e.preventDefault()
    setSaving(true)
    try {
      await updateAstrologerProfile(form)
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
      <h1 className="text-2xl font-bold text-gray-900 dark:text-white mb-6">Edit Astrologer Profile</h1>

      <form onSubmit={handleSubmit} className="bg-white dark:bg-gray-900 rounded-2xl border border-gray-100 dark:border-gray-800 p-8 space-y-6">
        <div>
          <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1.5">Bio</label>
          <textarea
            name="bio" value={form.bio} onChange={(e) => setForm({ ...form, bio: e.target.value })}
            rows={4} placeholder="Write about yourself, your experience, and expertise..."
            className="w-full px-4 py-2.5 rounded-xl border border-gray-200 dark:border-gray-700 bg-gray-50 dark:bg-gray-800 text-gray-900 dark:text-white text-sm focus:outline-none focus:ring-2 focus:ring-primary/30"
          />
        </div>

        <div className="grid grid-cols-2 gap-4">
          <div>
            <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1.5">Experience (years)</label>
            <input type="number" min={0} max={50} value={form.experience} onChange={(e) => setForm({ ...form, experience: Number(e.target.value) })}
              className="w-full px-4 py-2.5 rounded-xl border border-gray-200 dark:border-gray-700 bg-gray-50 dark:bg-gray-800 text-gray-900 dark:text-white text-sm focus:outline-none focus:ring-2 focus:ring-primary/30" />
          </div>
          <div>
            <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1.5">Price per Minute (₹)</label>
            <input type="number" min={1} max={1000} value={form.pricePerMin} onChange={(e) => setForm({ ...form, pricePerMin: Number(e.target.value) })}
              className="w-full px-4 py-2.5 rounded-xl border border-gray-200 dark:border-gray-700 bg-gray-50 dark:bg-gray-800 text-gray-900 dark:text-white text-sm focus:outline-none focus:ring-2 focus:ring-primary/30" />
          </div>
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">Specializations</label>
          <div className="flex flex-wrap gap-2">
            {specializations.map((spec) => (
              <button key={spec} type="button" onClick={() => setForm({ ...form, specializations: toggleArray(form.specializations, spec) })}
                className={`px-3 py-1.5 rounded-lg text-xs font-medium transition-colors ${form.specializations.includes(spec) ? 'bg-primary text-white' : 'bg-gray-100 dark:bg-gray-800 text-gray-600 dark:text-gray-400 hover:bg-gray-200 dark:hover:bg-gray-700'}`}>
                {spec}
              </button>
            ))}
          </div>
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">Languages</label>
          <div className="flex flex-wrap gap-2">
            {languages.map((lang) => (
              <button key={lang} type="button" onClick={() => setForm({ ...form, languages: toggleArray(form.languages, lang) })}
                className={`px-3 py-1.5 rounded-lg text-xs font-medium transition-colors ${form.languages.includes(lang) ? 'bg-primary text-white' : 'bg-gray-100 dark:bg-gray-800 text-gray-600 dark:text-gray-400 hover:bg-gray-200 dark:hover:bg-gray-700'}`}>
                {lang}
              </button>
            ))}
          </div>
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1.5">Profile Photo URL</label>
          <input type="url" value={form.profilePhoto} onChange={(e) => setForm({ ...form, profilePhoto: e.target.value })}
            placeholder="https://example.com/photo.jpg"
            className="w-full px-4 py-2.5 rounded-xl border border-gray-200 dark:border-gray-700 bg-gray-50 dark:bg-gray-800 text-gray-900 dark:text-white text-sm focus:outline-none focus:ring-2 focus:ring-primary/30" />
        </div>

        <button type="submit" disabled={saving}
          className="px-6 py-2.5 bg-primary text-white rounded-xl font-medium hover:bg-primary-dark transition-colors disabled:opacity-50">
          {saving ? 'Saving...' : 'Save Changes'}
        </button>
      </form>
    </div>
  )
}

export default AstrologerProfileEditPage
