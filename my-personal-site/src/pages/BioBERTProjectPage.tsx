import {
  DocLinks,
  FeatureList,
  ImageGallery,
  ProjectPageLayout,
  ProjectSection,
  SeverityScale,
  TechTable,
} from '../components/project/ProjectPageLayout'

export function BioBERTProjectPage() {
  return (
    <ProjectPageLayout
      title="BioBERT — Soccer Injury Severity Classification"
      subtitle="Applying a biomedical language model to predict recovery severity from Premier League injury reports using stratified fine-tuning and multi-class NLP evaluation."
      tags={['Python', 'BioBERT', 'PyTorch', 'Transformers', 'NLP']}
      heroImage={{
        src: '/projects/biobert/image8.jpg',
        alt: 'BioBERT injury severity classification pipeline',
      }}
    >
      <ProjectSection title="Purpose & Goals">
        <p>
          Athlete health drives competitive performance, yet there is no standardized formula to
          predict recovery timelines from injury text. This project fine tunes BioBERT on
          15,603 professional soccer injury records to classify severity into five tiers, from
          minor knocks to season-ending injuries.
        </p>
        <p>
          The goal is macro F1 above a majority-class baseline, useful precision on rare
          classes (e.g. season-ending), and demonstrated transfer of biomedical pretraining to
          sports-medicine vocabulary.
        </p>
      </ProjectSection>

      <ProjectSection title="Why BioBERT?">
        <p>
          BERT reads text bidirectionally and excels at general language, but medical terms like
          &ldquo;meniscectomy&rdquo; or &ldquo;hamstring&rdquo; are rare in vanilla BERT training data.
          BioBERT extends BERT with PubMed abstracts (4.5B words) and PMC full text (13.5B words),
          so it natrually understands clinical terminology.
        </p>
        <TechTable
          rows={[
            { label: 'Model', value: 'BioBERT (encoder-only transformer)' },
            { label: 'Framework', value: 'PyTorch + Hugging Face Transformers' },
            { label: 'Task', value: '5-class severity classification' },
            { label: 'Max sequence length', value: '128 tokens' },
            { label: 'Optimizer', value: 'AdamW (lr 2e-5, weight decay 0.01)' },
            { label: 'Split', value: 'Stratified 80/10/10 train/val/test' },
          ]}
        />
      </ProjectSection>

      <ProjectSection title="Severity Labels">
        <SeverityScale />
      </ProjectSection>

      <ProjectSection title="Dataset">
        <p>
          The <strong className="text-slate-900 dark:text-white">Player Injuries and Team Performance</strong>{' '}
          dataset (Kaggle, Amrit Biswas) combines Transfermarkt, soccer critic ratings, and
          SkySports sources across the EPL, La Liga, Bundesliga, and Serie A.
        </p>
        <FeatureList
          items={[
            '15,603 injury records across 600+ players',
            'Injury text descriptions with occurrence and expected return dates',
            'Match performance ratings before, during, and after absence',
            'Temporal windows: 3 matches pre-injury, missed matches, 3 post-return',
            'Labels derived from expected absence duration (tiers 1–5)',
          ]}
        />
        <p>
          A 80/10/10 split ensures rare classes (e.g. season-ending at ~3%) appear
          proportionally in train, validation, and test sets.
        </p>
      </ProjectSection>

      <ProjectSection title="Pipeline & Training">
        <p>
          End-to-end flow: label raw data → create severity tiers → clean injury text →
          stratified split → WordPiece tokenization → PyTorch dataset → fine-tune BioBERT →
          evaluate with macro F1, precision, recall, and per-class confusion matrix.
        </p>
        <TechTable
          rows={[
            { label: 'Batch size', value: '32' },
            { label: 'Epochs', value: '3–4' },
            { label: 'Loss', value: 'Cross-entropy' },
            { label: 'Warmup', value: '10% linear warmup' },
            { label: 'Evaluation', value: 'Accuracy, macro F1, per-class confusion matrix' },
          ]}
        />
      </ProjectSection>

      <ProjectSection title="Key Findings">
        <p>
          Accuracy alone is misleading on imbalanced severity data — predicting &ldquo;minor&rdquo; for
          everything could yield ~60% accuracy without real insight. The project tracks macro F1,
          precision, recall, and confusion matrices to see where the model confuses adjacent
          severities (e.g. Moderate vs Severe).
        </p>
        <p>
          BioBERT&apos;s biomedical pretraining improves tokenization of anatomy and procedures
          compared to vanilla BERT, supporting the hypothesis that injury reports behave like
          short clinical notes.
        </p>
      </ProjectSection>

      <ProjectSection title="Visualizations">
        <ImageGallery
          images={[
            {
              src: '/projects/biobert/BERTpipeline.png',
              alt: 'End-to-end BioBERT pipeline diagram',
              caption: 'Data preparation → training → evaluation pipeline',
            },
            {
              src: '/projects/biobert/biobert.png',
              alt: 'BERT vs BioBERT comparison',
              caption: 'Why BioBERT?',
            },
            {
              src: '/projects/biobert/image8.jpg',
              alt: 'Training Split',
              caption: 'Train/Validation/Test Split for BioBERT',
            },
          ]}
        />
      </ProjectSection>

      <ProjectSection title="Impact & Applications">
        <p>
          Reliable classification gives sports medicine staff time to plan
          team management, roster decisions, fantasy analytics, and injury prevention research.
          Future work includes richer datasets (youth, women&apos;s leagues), multimodal inputs, and
          domain specific error handling for colloquial injury descriptions.
        </p>
      </ProjectSection>

      <ProjectSection title="Documentation & Research">
        <DocLinks
          links={[
            {
              label: 'Download presentation (PPTX)',
              href: '/documents/biobert-presentation.pptx',
              external: true,
            },
          ]}
        />
        <p className="text-sm text-slate-500 dark:text-slate-400">
          Related work: Lee et al. (2019) BioBERT; Zhu et al. (2025) LLMs for sports injury.
          Notebooks: BioBERTv1–v4 in Python/NLP/BioBERT.
        </p>
      </ProjectSection>
    </ProjectPageLayout>
  )
}
