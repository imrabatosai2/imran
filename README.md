# Pulse

Pulse is a TikTok-style video discovery app for web and mobile. The initial web experience is implemented in Next.js with a responsive full-screen feed, topic filters, search, creator follow state, likes, comments, sharing, download feedback, and upload/sign-in entry points.

## Run the web app

```bash
npm install
npm run dev
```

Open `http://localhost:3000`.

## Product setup still needed

The UI is intentionally ready to connect to a real backend. Add Google OAuth, a database, and object/video storage before production:

- Google OAuth: Auth.js, Supabase Auth, or Firebase Auth
- Videos: Mux, Cloudflare Stream, or S3/R2 + a transcoding worker
- Database: PostgreSQL tables for users, videos, likes, comments, follows, views, and reports
- Search: PostgreSQL full-text search or Algolia
- Downloads: return signed public asset URLs from the video API and enforce creator moderation/rate limits

## Mobile

The same product can be shipped with Expo/React Native. The responsive web UI provides the first shared design system; use the `mobile` starter when adding native navigation and native media playback.
