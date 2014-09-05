Jive SBS Gravatar Plugin 
========================

Jive SBS Plugin provides avatars under Gravatar's style URLs.

Base gravatar url is `/gravatar/{emailhash}`

Size of avatar can be configured in URL parameter `s` e.g. `/gravatar/{emailhash}?size=46` or directly in URL e.g. `/gravatar/{emailhash}/46.png`

Email hash cache is incrementally modified when user is registered/deleted. Every hour cache is regenerated.
