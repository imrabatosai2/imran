"use client";

import { useMemo, useState } from "react";

type Video = {
  id: number;
  creator: string;
  handle: string;
  title: string;
  category: string;
  color: string;
  accent: string;
  likes: string;
  comments: string;
  duration: string;
  avatar: string;
};

const videos: Video[] = [
  { id: 1, creator: "Maya Chen", handle: "mayamakes", title: "The 10-minute reset that changed my mornings", category: "Lifestyle", color: "#ff8a4c", accent: "#ffd166", likes: "24.8K", comments: "682", duration: "0:28", avatar: "MC" },
  { id: 2, creator: "Omar Bello", handle: "omarcooks", title: "Crispy chili noodles with a five-ingredient sauce", category: "Food", color: "#7957e8", accent: "#f8b4ff", likes: "18.2K", comments: "391", duration: "0:41", avatar: "OB" },
  { id: 3, creator: "Jules Park", handle: "julesoutside", title: "A quiet corner of the city you need to see", category: "Travel", color: "#087f8c", accent: "#8de1c5", likes: "32.1K", comments: "924", duration: "0:36", avatar: "JP" },
  { id: 4, creator: "Sofia Lin", handle: "sofialin", title: "Three outfits, one pair of vintage jeans", category: "Style", color: "#e85176", accent: "#ffc2d1", likes: "11.7K", comments: "204", duration: "0:22", avatar: "SL" }
];

function Icon({ name }: { name: "search" | "heart" | "comment" | "download" | "share" | "play" | "plus" }) {
  const paths = {
    search: <><circle cx="11" cy="11" r="7"/><path d="m20 20-4-4"/></>,
    heart: <path d="M20.8 8.8c0 5.4-8.8 10.2-8.8 10.2S3.2 14.2 3.2 8.8A4.8 4.8 0 0 1 12 6.3a4.8 4.8 0 0 1 8.8 2.5Z"/>,
    comment: <path d="M20 11.5a7.5 7.5 0 0 1-8 7.5 9 9 0 0 1-4-.9L4 20l1.5-3.2A7.2 7.2 0 0 1 4 11.5 7.5 7.5 0 0 1 12 4a7.5 7.5 0 0 1 8 7.5Z"/>,
    download: <><path d="M12 3v11"/><path d="m7 10 5 5 5-5"/><path d="M4 20h16"/></>,
    share: <><circle cx="18" cy="5" r="2"/><circle cx="6" cy="12" r="2"/><circle cx="18" cy="19" r="2"/><path d="m8 11 8-5M8 13l8 5"/></>,
    play: <path d="m9 6 9 6-9 6V6Z"/>,
    plus: <><path d="M12 5v14M5 12h14"/></>
  } as const;
  return <svg aria-hidden="true" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">{paths[name]}</svg>;
}

export default function Home() {
  const [active, setActive] = useState(0);
  const [liked, setLiked] = useState<number[]>([]);
  const [query, setQuery] = useState("");
  const [notice, setNotice] = useState("");
  const [following, setFollowing] = useState<number[]>([]);
  const filtered = useMemo(() => videos.filter(video => `${video.title} ${video.creator} ${video.category}`.toLowerCase().includes(query.toLowerCase())), [query]);
  const current = filtered[active] ?? filtered[0];

  if (!current) return <main className="empty"><h1>No videos found</h1><button onClick={() => setQuery("")}>Clear search</button></main>;

  const flash = (message: string) => { setNotice(message); window.setTimeout(() => setNotice(""), 2200); };

  return <main className="app-shell">
    <header className="topbar">
      <div className="brand"><span className="brand-mark">✦</span><span>pulse</span></div>
      <nav className="desktop-nav"><button className="nav-active">For you</button><button>Following</button><button>Live</button></nav>
      <div className="header-actions"><label className="search"><Icon name="search"/><input value={query} onChange={e => { setQuery(e.target.value); setActive(0); }} placeholder="Search videos" /></label><button className="upload" onClick={() => flash("Upload studio is ready for your next post") }><Icon name="plus"/> <span>Upload</span></button><button className="avatar-button" onClick={() => flash("Sign in with Google to continue")}>IM</button></div>
    </header>

    <section className="content">
      <div className="feed-heading"><div><p className="eyebrow">Good afternoon, Imran</p><h1>Find your next <em>favorite</em></h1></div><button className="filter" onClick={() => flash("Showing the latest community picks")}>Latest <span>⌄</span></button></div>
      <div className="feed-wrap">
        <article className="video-card" style={{ background: `linear-gradient(145deg, ${current.color}, #191827 78%)` }}>
          <div className="video-glow" style={{ background: current.accent }} />
          <div className="video-art"><div className="art-orbit"/><div className="art-title">{current.category}<br/><strong>in motion</strong></div><span className="art-sticker">PULSE<br/>PLAY</span></div>
          <div className="video-top"><span className="duration"><Icon name="play"/> {current.duration}</span><span className="verified">✦ Creator pick</span></div>
          <div className="video-bottom"><div className="video-copy"><span className="category">{current.category}</span><h2>{current.title}</h2><p><b>@{current.handle}</b> · {current.creator}</p></div><button className="mute" onClick={() => flash("Sound toggled")}>⌁</button></div>
        </article>
        <aside className="engagement"><button className={`creator-avatar ${following.includes(current.id) ? "is-following" : ""}`} onClick={() => { setFollowing(f => f.includes(current.id) ? f.filter(id => id !== current.id) : [...f, current.id]); flash(following.includes(current.id) ? "Unfollowed creator" : "Following creator"); }}>{current.avatar}<span>+</span></button><Action icon="heart" label={current.likes} active={liked.includes(current.id)} onClick={() => setLiked(l => l.includes(current.id) ? l.filter(id => id !== current.id) : [...l, current.id])}/><Action icon="comment" label={current.comments} onClick={() => flash("Comments opened")}/><Action icon="download" label="Save" onClick={() => flash("Download started")}/><Action icon="share" label="Share" onClick={() => flash("Link copied")}/></aside>
      </div>
      <div className="pager"><button onClick={() => setActive(Math.max(0, active - 1))}>←</button><div className="progress"><span style={{ width: `${((active + 1) / filtered.length) * 100}%` }}/></div><span>{String(active + 1).padStart(2, "0")} / {String(filtered.length).padStart(2, "0")}</span><button onClick={() => setActive((active + 1) % filtered.length)}>→</button></div>
      <div className="topic-row"><span>Explore topics</span>{["All", "Lifestyle", "Food", "Travel", "Style"].map((topic, i) => <button key={topic} className={i === 0 ? "topic-active" : ""} onClick={() => setQuery(i === 0 ? "" : topic)}>{topic}</button>)}</div>
    </section>
    {notice && <div className="toast">{notice}</div>}
  </main>;
}

function Action({ icon, label, active, onClick }: { icon: "heart" | "comment" | "download" | "share"; label: string; active?: boolean; onClick: () => void }) { return <button className={`action ${active ? "liked" : ""}`} onClick={onClick}><span><Icon name={icon}/></span><small>{label}</small></button>; }
