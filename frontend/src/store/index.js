import { configureStore } from '@reduxjs/toolkit'
import authReducer from '../features/auth/store/authSlice'
import walletReducer from '../features/wallet/store/walletSlice'
import consultationReducer from '../features/consultation/store/consultationSlice'
import notificationReducer from '../features/admin/store/notificationSlice'
import astrologerReducer from '../features/astrologer/store/astrologerSlice'

export const store = configureStore({
  reducer: {
    auth: authReducer,
    wallet: walletReducer,
    consultation: consultationReducer,
    notification: notificationReducer,
    astrologer: astrologerReducer,
  },
})
