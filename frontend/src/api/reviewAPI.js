import axiosInstance from './axiosInstance'

export const submitReview = (data) =>
  axiosInstance.post('/reviews', data)

export const getReviewsByAstrologer = (astrologerId, params) =>
  axiosInstance.get(`/reviews/astrologer/${astrologerId}`, { params })
