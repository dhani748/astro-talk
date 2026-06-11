import axiosInstance from './axiosInstance'

export const getAstrologers = (params) =>
  axiosInstance.get('/astrologers', { params })

export const getAstrologerProfile = (id) =>
  axiosInstance.get(`/astrologers/${id}`)

export const toggleAstrologerStatus = () =>
  axiosInstance.patch('/astrologers/status')

export const updateAstrologerProfile = (data) =>
  axiosInstance.put('/astrologers/profile', data)

export const getTopAstrologers = () =>
  axiosInstance.get('/astrologers/top')
