import { Routes, Route } from 'react-router-dom'
import { Layout } from './components/Layout'
import { ScrollToHash } from './components/ScrollToHash'
import { HomePage } from './pages/HomePage'
import { UnoProjectPage } from './pages/UnoProjectPage'
import { ChattyProjectPage } from './pages/ChattyProjectPage'
import { BioBERTProjectPage } from './pages/BioBERTProjectPage'

function App() {
  return (
    <Layout>
      <ScrollToHash />
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/projects/uno" element={<UnoProjectPage />} />
        <Route path="/projects/chatty" element={<ChattyProjectPage />} />
        <Route path="/projects/biobert" element={<BioBERTProjectPage />} />
      </Routes>
    </Layout>
  )
}

export default App
