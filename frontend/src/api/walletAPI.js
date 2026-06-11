import axiosInstance from './axiosInstance'

export const getWalletBalance = () =>
  axiosInstance.get('/wallet/balance')

export const addMoney = (amount) =>
  axiosInstance.post('/wallet/add', { amount })

export const getTransactions = (params) =>
  axiosInstance.get('/wallet/transactions', { params })

export const createRazorpayOrder = (amount) =>
  axiosInstance.post('/payments/create-order', { amount })

export const verifyPayment = (data) =>
  axiosInstance.post('/payments/verify', data)
