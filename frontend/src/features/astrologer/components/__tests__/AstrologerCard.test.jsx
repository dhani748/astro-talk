import { render, screen } from '@testing-library/react'
import { MemoryRouter } from 'react-router-dom'
import AstrologerCard from '../AstrologerCard'

const mockAstrologer = {
  id: '1',
  name: 'Test Astrologer',
  specialization: 'Vedic',
  rating: 4.5,
  reviewCount: 100,
  pricePerMin: 15,
  online: true,
  profilePhoto: null,
}

const renderCard = (astrologer = mockAstrologer) => {
  return render(
    <MemoryRouter>
      <AstrologerCard astrologer={astrologer} />
    </MemoryRouter>
  )
}

describe('AstrologerCard', () => {
  it('should render astrologer name', () => {
    renderCard()
    expect(screen.getByText('Test Astrologer')).toBeInTheDocument()
  })

  it('should render specialization', () => {
    renderCard()
    expect(screen.getByText('Vedic')).toBeInTheDocument()
  })

  it('should render rating', () => {
    renderCard()
    expect(screen.getByText('4.5')).toBeInTheDocument()
  })

  it('should render review count', () => {
    renderCard()
    expect(screen.getByText('(100)')).toBeInTheDocument()
  })

  it('should render price per minute', () => {
    renderCard()
    expect(screen.getByText('₹15/min')).toBeInTheDocument()
  })

  it('should render online badge when astrologer is online', () => {
    renderCard()
    expect(screen.getByText('online')).toBeInTheDocument()
  })

  it('should render offline badge when astrologer is offline', () => {
    renderCard({ ...mockAstrologer, online: false })
    expect(screen.getByText('offline')).toBeInTheDocument()
  })
})
