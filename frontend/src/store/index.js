import { configureStore } from '@reduxjs/toolkit'
import authReducer from './authSlice'
import walletReducer from './walletSlice'
import consultationReducer from './consultationSlice'
import notificationReducer from './notificationSlice'
import astrologerReducer from './astrologerSlice'

export const store = configureStore({
  reducer: {
    auth: authReducer,
    wallet: walletReducer,
    consultation: consultationReducer,
    notification: notificationReducer,
    astrologer: astrologerReducer,
  },
})
