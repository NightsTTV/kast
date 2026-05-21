import {
  DocLinks,
  FeatureList,
  ImageGallery,
  ProjectPageLayout,
  ProjectSection,
  TechTable,
} from '../components/project/ProjectPageLayout'

export function UnoProjectPage() {
  return (
    <ProjectPageLayout
      title="Multiplayer UNO Card Game"
      subtitle="A fully functional multiplayer UNO implementation using Java socket programming, a thread-safe concurrent server, and a Swing GUI client for 2–4 players."
      tags={['Java', 'TCP Sockets', 'Swing', 'Multithreading', 'Client-Server']}
      heroImage={{
        src: '/projects/uno/cover.jpg',
        alt: 'UNO multiplayer project cover',
      }}
    >
      <ProjectSection title="Overview">
        <p>
          This project reinvents the classic UNO card game with a client-server architecture. Players connect to a central
          Java server over TCP, join game rooms automatically, and play in real time while the
          server enforces official rules, manages turns, and synchronizes state across every
          client.
        </p>
        <p>
          The server is authoritative, meaning all game logic runs on the backend so that clients cannot
          cheat. Each player uses a Swing GUI to view their hand, the top card, game status,
          and chat with other players in the room. The GUI is fully functional and allows for the user to play the game.
        </p>
      </ProjectSection>

      <ProjectSection title="Rules & Gameplay">
        <p>
          UNO follows standard rules: match cards by color, number, or symbol. Number cards
          (0–9) appear in four colors. Special cards include Skip, Reverse, Draw Two, Wild,
          and Wild Draw Four.
        </p>
        <ul className="list-disc space-y-2 pl-5">
          <li>2–4 players per room; games auto-start when the lobby fills or after a 10-second countdown</li>
          <li>Each player starts with 7 cards; play matches the top discard by color, number, or type</li>
          <li>Wild cards let the player choose the active color; Wild Draw Four forces the next player to draw 4 and skip</li>
          <li>Skip passes the next player; Reverse toggles play direction (acts like Skip in 2-player games)</li>
          <li>Draw Two forces the victim to draw 2 cards and lose their turn</li>
          <li>Built-in chat lets players communicate during the match</li>
        </ul>
      </ProjectSection>

      <ProjectSection title="Key Features">
        <FeatureList
          items={[
            'Client-server architecture on port 8888 with TCP sockets',
            'Automatic matchmaking into game rooms (2–4 players)',
            '10-second countdown timer with TIMER_TICK broadcasts',
            'Complete 108-card deck with official special-card effects',
            'Thread-safe GameRoom with synchronized state updates',
            'Real-time hand, top card, and turn synchronization',
            'Swing GUI client with interactive hand and draw button',
            'In-game chat broadcast to all players in a room',
            'Serializable Card objects for network transmission',
          ]}
        />
      </ProjectSection>

      <ProjectSection title="Architecture & Technologies">
        <p>
          The system uses a centralized server model. The main thread accepts connections;
          each client gets a <code className="text-sky-600 dark:text-sky-400">ClientHandler</code>{' '}
          from a cached thread pool. Game rooms live in a{' '}
          <code className="text-sky-600 dark:text-sky-400">ConcurrentHashMap</code> so multiple
          matches run in parallel without blocking.
        </p>
        <TechTable
          rows={[
            { label: 'Language', value: 'Java' },
            { label: 'Networking', value: 'TCP/IP sockets (port 8888)' },
            { label: 'Concurrency', value: 'ExecutorService, synchronized methods, ScheduledExecutorService' },
            { label: 'GUI', value: 'Java Swing (UNOClient)' },
            { label: 'Protocol', value: 'Pipe-delimited text (PLAY_CARD, DRAW_CARD, CHAT, etc.)' },
            { label: 'Core classes', value: 'UNOServer, GameRoom, Player, Card, UNOClient' },
          ]}
        />
        <p className="mt-4">
          <strong className="text-slate-900 dark:text-white">Sockets</strong> provide reliable
          bidirectional communication. <strong className="text-slate-900 dark:text-white">Multithreading</strong>{' '}
          isolates each client while the server coordinates shared game state.{' '}
          <strong className="text-slate-900 dark:text-white">Game state management</strong> keeps
          deck, discard pile, turn order, direction, and current color consistent via server-side
          broadcasts after every valid action.
        </p>
      </ProjectSection>

      <ProjectSection title="Screenshots">
        <ImageGallery
          images={[
            {
              src: '/projects/uno/cover.jpg',
              alt: 'UNO project title graphic',
              caption: 'Project overview — multiplayer UNO with client-server architecture',
            },
            {
              src: '/projects/uno/sysarch.png',
              alt: 'UNO system architecture diagram',
              caption: 'System architecture and class relationships',
            },
          ]}
        />
      </ProjectSection>

      <ProjectSection title="How to Run">
        <pre className="overflow-x-auto rounded-xl border border-slate-200 bg-slate-900 p-4 text-sm text-slate-100 dark:border-white/10">
{`# Terminal 1 — Server
javac *.java
java UNOServer

# Terminal 2+ — Clients (repeat for each player)
java UNOClient
# Enter username, connect to localhost`}
        </pre>
      </ProjectSection>

      <ProjectSection title="Documentation & Source">
        <p>
          Full technical documentation covers class design, network protocol, special card
          effects, concurrency patterns, and future implementations.
        </p>
        <DocLinks
          links={[
            { label: 'Download full report (PDF)', href: '/documents/uno-report.pdf', external: true },
          ]}
        />
        <p className="text-sm text-slate-500 dark:text-slate-400">
          Source: Java/UNO (UNOServer, GameRoom, Player, Card, UNOClient). 
        </p>
      </ProjectSection>
    </ProjectPageLayout>
  )
}
