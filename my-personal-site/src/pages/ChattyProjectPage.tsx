import {
  DocLinks,
  FeatureList,
  ImageGallery,
  ProjectPageLayout,
  ProjectSection,
  TechTable,
} from '../components/project/ProjectPageLayout'

export function ChattyProjectPage() {
  return (
    <ProjectPageLayout
      title="Chatty — Multi-User Chat Application"
      subtitle="A real-time, multi-client chat application with a TCP socket server and a themed Java Swing GUI — no third-party networking frameworks required."
      tags={['Java', 'Swing', 'TCP Sockets', 'Multithreading', 'GUI']}
      heroImage={{
        src: '/projects/chatty/page7_img1.jpg',
        alt: 'Chatty login screen',
      }}
    >
      <ProjectSection title="Overview">
        <p>
          Chatty is a chat system where multiple users connect to a single
          server, register unique usernames, and exchange messages in real time. The server
          broadcasts every message to all connected clients while tracking who is online.
        </p>
        <p>
          The graphical client separates networking (<code className="text-sky-600 dark:text-sky-400">ChatClient</code>)
          from the UI (<code className="text-sky-600 dark:text-sky-400">Dashboard</code>), and a
          CLI client is included for quick testing without the GUI.
        </p>
      </ProjectSection>

      <ProjectSection title="Features & Functionality">
        <FeatureList
          items={[
            'Real-time global messaging via server broadcast',
            'Unique username registration before chatting',
            'Live active-user list (type AllUsers or use sidebar)',
            'Themed Swing login screen (Start.java) with server IP, port, username',
            'Dashboard with chat area, collapsible user sidebar, and input field',
            'Graceful disconnect with Bye command',
            'Timestamped server logging for debugging',
            'CLI alternative client for terminal-based testing',
          ]}
        />
      </ProjectSection>

      <ProjectSection title="Client-Server Architecture">
        <p>
          The server listens on TCP port 1234. Each accepted socket spawns a dedicated{' '}
          <code className="text-sky-600 dark:text-sky-400">ClientHandler</code> thread. Active
          clients are stored in a thread-safe{' '}
          <code className="text-sky-600 dark:text-sky-400">ConcurrentHashMap&lt;String, PrintWriter&gt;</code>{' '}
          so broadcasts never block the accept loop.
        </p>
        <p>
          On the client, <code className="text-sky-600 dark:text-sky-400">ChatClient.startListening()</code>{' '}
          runs a daemon reader thread and delivers each incoming line to the Swing EDT via{' '}
          <code className="text-sky-600 dark:text-sky-400">SwingUtilities.invokeLater()</code>,
          keeping the UI responsive and thread-safe.
        </p>
        <TechTable
          rows={[
            { label: 'Server', value: 'Server.java — ServerSocket, broadcast, user map' },
            { label: 'GUI entry', value: 'Start.java — login & connection' },
            { label: 'Chat window', value: 'Dashboard.java — messages & user list' },
            { label: 'Socket layer', value: 'ChatClient.java — connect, send, listen' },
            { label: 'CLI client', value: 'Client.java — stdin/stdout chat' },
            { label: 'Port', value: '1234 (TCP)' },
          ]}
        />
      </ProjectSection>

      <ProjectSection title="Connecting & Messaging">
        <ol className="list-decimal space-y-2 pl-5">
          <li>Launch the server: <code className="text-sky-600 dark:text-sky-400">java Server</code></li>
          <li>Open the GUI: <code className="text-sky-600 dark:text-sky-400">java Start</code></li>
          <li>Enter server IP (default localhost), port (1234), and a unique username</li>
          <li>Server prompts <code className="text-sky-600 dark:text-sky-400">Username =</code> — client sends the name</li>
          <li>After welcome, type messages in the dashboard; server broadcasts as <code className="text-sky-600 dark:text-sky-400">username: message</code></li>
          <li>Type <code className="text-sky-600 dark:text-sky-400">AllUsers</code> to refresh the online roster</li>
          <li>Send <code className="text-sky-600 dark:text-sky-400">Bye</code> to disconnect cleanly</li>
        </ol>
      </ProjectSection>

      <ProjectSection title="Screenshots">
        <ImageGallery
          images={[
            {
              src: '/projects/chatty/page6_img2.jpg',
              alt: 'Chatty login screen mockup',
              caption: 'Login screen — server IP, port, and username (Start.java)',
            },
            {
              src: '/projects/chatty/page7_img1.jpg',
              alt: 'Chatty dashboard mockup',
              caption: 'Dashboard with chat area, sidebar, and active users (Dashboard.java)',
            },
          ]}
        />
      </ProjectSection>

      <ProjectSection title="Technologies & Concepts">
        <p>
          <strong className="text-slate-900 dark:text-white">Sockets</strong> handle persistent
          TCP streams. <strong className="text-slate-900 dark:text-white">Multithreading</strong>{' '}
          gives each client isolated I/O. <strong className="text-slate-900 dark:text-white">GUI programming</strong>{' '}
          with Swing provides themed layouts, scrollable chat history, and sidebar toggling.
          Plain text newline delimited messages keep the protocol easy to debug and extend.
        </p>
      </ProjectSection>

      <ProjectSection title="How to Run">
        <pre className="overflow-x-auto rounded-xl border border-slate-200 bg-slate-900 p-4 text-sm text-slate-100 dark:border-white/10">
{`javac Server.java ChatClient.java Dashboard.java Start.java Client.java

# Terminal 1
java Server

# Terminal 2+ (GUI)
java Start

# Optional CLI client
java Client localhost 1234`}
        </pre>
      </ProjectSection>

      <ProjectSection title="Documentation & Source">
        <DocLinks
          links={[
            { label: 'Download full report (PDF)', href: '/documents/chatty-report.pdf', external: true },
          ]}
        />
        <p className="text-sm text-slate-500 dark:text-slate-400">
          Source: Java/Prac/Chatty. (Note: Place bck.png alongside compiled classes for the login
          background image.)
        </p>
      </ProjectSection>
    </ProjectPageLayout>
  )
}
