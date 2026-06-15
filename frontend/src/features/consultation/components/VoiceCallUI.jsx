import { FiMic, FiMicOff, FiVolume2, FiVolumeX, FiPhoneOff } from 'react-icons/fi'
import CallTimer from './CallTimer'

const VoiceCallUI = ({ onEndCall, muted, speakerOn, onToggleMute, onToggleSpeaker, astrologer }) => {
  return (
    <div className="bg-gradient-to-br from-primary to-primary-dark rounded-2xl p-8 text-center min-h-[400px] flex flex-col items-center justify-center">
      <div className="w-28 h-28 bg-white/20 rounded-full flex items-center justify-center mb-6 animate-pulse">
        <img
          src={astrologer?.profilePhoto || `https://ui-avatars.com/api/?name=${astrologer?.name || 'A'}&background=fff&color=6B21A8&size=80`}
          alt={astrologer?.name}
          className="w-24 h-24 rounded-full object-cover"
        />
      </div>
      <h3 className="text-xl font-semibold text-white mb-1">{astrologer?.name || 'Connecting...'}</h3>
      <CallTimer />
      <p className="text-white/60 text-sm mt-2">Voice Call</p>

      <div className="flex items-center gap-4 mt-8">
        <button
          onClick={onToggleMute}
          className={`p-4 rounded-full transition-colors ${muted ? 'bg-red-500 text-white' : 'bg-white/20 text-white hover:bg-white/30'}`}
        >
          {muted ? <FiMicOff size={24} /> : <FiMic size={24} />}
        </button>
        <button
          onClick={onEndCall}
          className="p-4 bg-red-500 text-white rounded-full hover:bg-red-600 transition-colors"
        >
          <FiPhoneOff size={24} />
        </button>
        <button
          onClick={onToggleSpeaker}
          className={`p-4 rounded-full transition-colors ${speakerOn ? 'bg-white/30 text-white' : 'bg-white/20 text-white hover:bg-white/30'}`}
        >
          {speakerOn ? <FiVolume2 size={24} /> : <FiVolumeX size={24} />}
        </button>
      </div>
    </div>
  )
}

export default VoiceCallUI
