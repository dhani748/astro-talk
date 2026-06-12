const Badge = ({ status, children }) => {
  const colors = {
    online: { bg: 'rgba(201,168,76,0.15)', text: 'var(--gold)', dot: 'var(--gold)' },
    offline: { bg: 'rgba(255,255,255,0.05)', text: 'var(--muted)', dot: 'var(--muted)' },
    busy: { bg: 'rgba(226,75,74,0.15)', text: '#E24B4A', dot: '#E24B4A' },
    verified: { bg: 'rgba(201,168,76,0.15)', text: 'var(--gold)', dot: 'var(--gold)' },
  }
  const c = colors[status] || colors.offline

  return (
    <span
      style={{
        display: 'inline-flex', alignItems: 'center', gap: '4px',
        padding: '2px 8px', borderRadius: '999px',
        fontSize: '.6rem', fontWeight: 500, letterSpacing: '.1em',
        textTransform: 'uppercase',
        background: c.bg, color: c.text,
      }}
    >
      <span style={{ width: 5, height: 5, borderRadius: '50%', background: c.dot, flexShrink: 0 }} />
      {children || status}
    </span>
  )
}

export default Badge
