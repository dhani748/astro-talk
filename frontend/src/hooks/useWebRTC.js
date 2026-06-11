import { useState, useRef, useCallback, useEffect } from 'react'
import SimplePeer from 'simple-peer'

const useWebRTC = (sendMessage) => {
  const [localStream, setLocalStream] = useState(null)
  const [remoteStream, setRemoteStream] = useState(null)
  const [callStatus, setCallStatus] = useState('idle')
  const peerRef = useRef(null)

  const getLocalStream = useCallback(async () => {
    try {
      const stream = await navigator.mediaDevices.getUserMedia({
        video: true,
        audio: true,
      })
      setLocalStream(stream)
      return stream
    } catch (err) {
      console.error('Failed to get local stream:', err)
      throw err
    }
  }, [])

  const initiateCall = useCallback(async (consultationId, type) => {
    try {
      const stream = await getLocalStream()
      setCallStatus('initiating')

      const peer = new SimplePeer({
        initiator: true,
        trickle: false,
        stream,
      })

      peer.on('signal', (data) => {
        sendMessage(`/app/consultation/${consultationId}/signal`, {
          type: 'offer',
          signal: data,
          consultationType: type,
        })
      })

      peer.on('stream', (stream) => {
        setRemoteStream(stream)
        setCallStatus('connected')
      })

      peer.on('close', () => {
        setCallStatus('ended')
        cleanup()
      })

      peer.on('error', (err) => {
        console.error('Peer error:', err)
        setCallStatus('error')
      })

      peerRef.current = peer
    } catch (err) {
      setCallStatus('error')
    }
  }, [getLocalStream, sendMessage])

  const answerCall = useCallback(async (signal) => {
    try {
      const stream = await getLocalStream()
      setCallStatus('connecting')

      const peer = new SimplePeer({
        initiator: false,
        trickle: false,
        stream,
      })

      peer.on('signal', (data) => {
        sendMessage('/app/consultation/signal', {
          type: 'answer',
          signal: data,
        })
      })

      peer.on('stream', (stream) => {
        setRemoteStream(stream)
        setCallStatus('connected')
      })

      peer.on('close', () => {
        setCallStatus('ended')
        cleanup()
      })

      peer.on('error', (err) => {
        console.error('Peer error:', err)
        setCallStatus('error')
      })

      peer.signal(signal)
      peerRef.current = peer
    } catch (err) {
      setCallStatus('error')
    }
  }, [getLocalStream, sendMessage])

  const endCall = useCallback(() => {
    if (peerRef.current) {
      peerRef.current.destroy()
    }
    cleanup()
    setCallStatus('ended')
  }, [])

  const cleanup = useCallback(() => {
    if (localStream) {
      localStream.getTracks().forEach((track) => track.stop())
    }
    setLocalStream(null)
    setRemoteStream(null)
    peerRef.current = null
  }, [localStream])

  useEffect(() => {
    return () => {
      if (peerRef.current) {
        peerRef.current.destroy()
      }
      if (localStream) {
        localStream.getTracks().forEach((track) => track.stop())
      }
    }
  }, [])

  return { localStream, remoteStream, callStatus, initiateCall, answerCall, endCall }
}

export default useWebRTC
