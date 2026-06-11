import { createSlice } from '@reduxjs/toolkit'

const initialState = {
  balance: 0,
  transactions: [],
  loading: false,
  error: null,
}

const walletSlice = createSlice({
  name: 'wallet',
  initialState,
  reducers: {
    setBalance: (state, action) => {
      state.balance = action.payload
    },
    addBalance: (state, action) => {
      state.balance += action.payload
    },
    deductBalance: (state, action) => {
      state.balance -= action.payload
    },
    setTransactions: (state, action) => {
      state.transactions = action.payload
    },
    addTransaction: (state, action) => {
      state.transactions.unshift(action.payload)
    },
    setLoading: (state, action) => {
      state.loading = action.payload
    },
    setError: (state, action) => {
      state.error = action.payload
    },
  },
})

export const { setBalance, addBalance, deductBalance, setTransactions, addTransaction, setLoading, setError } = walletSlice.actions
export default walletSlice.reducer
