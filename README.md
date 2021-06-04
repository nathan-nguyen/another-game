# network-client-server

## Architecture

Server
- serverId
- communicationManager
- startService()
    - Start thread with ClientListenerRunnable(serverId, communicationManager)
  
Client
- clientId
- hostname
- port
- communicationManager

