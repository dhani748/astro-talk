import { BrowserRouter, Routes, Route, Outlet } from 'react-router-dom'
import { Provider } from 'react-redux'
import { Toaster } from 'react-hot-toast'
import { store } from './store'
import Navbar from './components/layout/Navbar'
import Footer from './components/layout/Footer'
import Sidebar from './components/layout/Sidebar'
import ProtectedRoute from './components/layout/ProtectedRoute'
import RoleRoute from './components/layout/RoleRoute'

import HomePage from './pages/public/HomePage'
import AstrologerListPage from './pages/public/AstrologerListPage'
import AstrologerProfilePage from './pages/public/AstrologerProfilePage'
import LoginPage from './pages/public/LoginPage'
import RegisterPage from './pages/public/RegisterPage'

import DashboardPage from './pages/user/DashboardPage'
import WalletPage from './pages/user/WalletPage'
import ConsultationPage from './pages/user/ConsultationPage'
import HistoryPage from './pages/user/HistoryPage'
import ProfilePage from './pages/user/ProfilePage'

import AstrologerDashboardPage from './pages/astrologer/AstrologerDashboardPage'
import AstrologerProfileEditPage from './pages/astrologer/AstrologerProfileEditPage'
import EarningsPage from './pages/astrologer/EarningsPage'

import AdminDashboardPage from './pages/admin/AdminDashboardPage'
import AdminUsersPage from './pages/admin/AdminUsersPage'
import AdminAstrologersPage from './pages/admin/AdminAstrologersPage'
import AdminRevenuePage from './pages/admin/AdminRevenuePage'

const PublicLayout = () => (
  <div className="min-h-screen flex flex-col bg-white dark:bg-gray-950 text-gray-900 dark:text-gray-100">
    <Navbar />
    <main className="flex-1">
      <Outlet />
    </main>
    <Footer />
  </div>
)

const DashboardLayout = () => (
  <div className="min-h-screen flex flex-col bg-gray-50 dark:bg-gray-950">
    <Navbar />
    <div className="flex-1 flex max-w-7xl mx-auto w-full px-4 sm:px-6 lg:px-8 py-8 gap-8">
      <Sidebar />
      <main className="flex-1 min-w-0">
        <Outlet />
      </main>
    </div>
  </div>
)

const ConsultationLayout = () => (
  <div className="min-h-screen flex flex-col bg-gray-50 dark:bg-gray-950">
    <Navbar />
    <main className="flex-1">
      <Outlet />
    </main>
  </div>
)

function App() {
  return (
    <Provider store={store}>
      <BrowserRouter>
        <Toaster
          position="top-right"
          toastOptions={{
            duration: 3000,
            style: {
              borderRadius: '12px',
              background: '#333',
              color: '#fff',
              fontSize: '14px',
            },
          }}
        />
        <Routes>
          <Route element={<PublicLayout />}>
            <Route path="/" element={<HomePage />} />
            <Route path="/astrologers" element={<AstrologerListPage />} />
            <Route path="/astrologers/:id" element={<AstrologerProfilePage />} />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/register" element={<RegisterPage />} />
          </Route>

          <Route element={<ProtectedRoute><DashboardLayout /></ProtectedRoute>}>
            <Route path="/dashboard" element={<DashboardPage />} />
            <Route path="/wallet" element={<WalletPage />} />
            <Route path="/history" element={<HistoryPage />} />
            <Route path="/profile" element={<ProfilePage />} />
          </Route>

          <Route element={<ProtectedRoute><ConsultationLayout /></ProtectedRoute>}>
            <Route path="/consultation/:id" element={<ConsultationPage />} />
          </Route>

          <Route element={<ProtectedRoute><RoleRoute allowedRoles={['astro']}><DashboardLayout /></RoleRoute></ProtectedRoute>}>
            <Route path="/astrologer/dashboard" element={<AstrologerDashboardPage />} />
            <Route path="/astrologer/profile" element={<AstrologerProfileEditPage />} />
            <Route path="/astrologer/earnings" element={<EarningsPage />} />
          </Route>

          <Route element={<ProtectedRoute><RoleRoute allowedRoles={['admin']}><DashboardLayout /></RoleRoute></ProtectedRoute>}>
            <Route path="/admin/dashboard" element={<AdminDashboardPage />} />
            <Route path="/admin/users" element={<AdminUsersPage />} />
            <Route path="/admin/astrologers" element={<AdminAstrologersPage />} />
            <Route path="/admin/revenue" element={<AdminRevenuePage />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </Provider>
  )
}

export default App
