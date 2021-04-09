--
-- PostgreSQL database cluster dump
--

SET default_transaction_read_only = off;

SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;

--
-- Drop databases (except postgres and template1)
--

DROP DATABASE db;




--
-- Drop roles
--

DROP ROLE postgres;


--
-- Roles
--

CREATE ROLE postgres;
ALTER ROLE postgres WITH SUPERUSER INHERIT CREATEROLE CREATEDB LOGIN REPLICATION BYPASSRLS PASSWORD 'md5e223a9a50eab9b4ba4cd0ef59288dfe3';






--
-- Databases
--

--
-- Database "template1" dump
--

--
-- PostgreSQL database dump
--

-- Dumped from database version 13.1 (Debian 13.1-1.pgdg100+1)
-- Dumped by pg_dump version 13.1 (Debian 13.1-1.pgdg100+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

UPDATE pg_catalog.pg_database SET datistemplate = false WHERE datname = 'template1';
DROP DATABASE template1;
--
-- Name: template1; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE template1 WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US.utf8';


ALTER DATABASE template1 OWNER TO postgres;

\connect template1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: DATABASE template1; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON DATABASE template1 IS 'default template for new databases';


--
-- Name: template1; Type: DATABASE PROPERTIES; Schema: -; Owner: postgres
--

ALTER DATABASE template1 IS_TEMPLATE = true;


\connect template1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: DATABASE template1; Type: ACL; Schema: -; Owner: postgres
--

REVOKE CONNECT,TEMPORARY ON DATABASE template1 FROM PUBLIC;
GRANT CONNECT ON DATABASE template1 TO PUBLIC;


--
-- PostgreSQL database dump complete
--

--
-- Database "db" dump
--

--
-- PostgreSQL database dump
--

-- Dumped from database version 13.1 (Debian 13.1-1.pgdg100+1)
-- Dumped by pg_dump version 13.1 (Debian 13.1-1.pgdg100+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: db; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE db WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US.utf8';


ALTER DATABASE db OWNER TO postgres;

\connect db

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: comments; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.comments (
    id bigint NOT NULL,
    user_id bigint NOT NULL,
    post_type bigint NOT NULL,
    post_id bigint NOT NULL,
    message text NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone
);


ALTER TABLE public.comments OWNER TO postgres;

--
-- Name: comments_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.comments_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.comments_id_seq OWNER TO postgres;

--
-- Name: comments_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.comments_id_seq OWNED BY public.comments.id;


--
-- Name: comments_post_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.comments_post_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.comments_post_id_seq OWNER TO postgres;

--
-- Name: comments_post_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.comments_post_id_seq OWNED BY public.comments.post_id;


--
-- Name: complaints; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.complaints (
    id bigint NOT NULL,
    user_id bigint NOT NULL,
    post_type bigint NOT NULL,
    post_id bigint NOT NULL,
    post_author bigint NOT NULL,
    reason integer NOT NULL,
    message character varying(2000) NOT NULL,
    created timestamp without time zone NOT NULL
);


ALTER TABLE public.complaints OWNER TO postgres;

--
-- Name: complaints_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.complaints_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.complaints_id_seq OWNER TO postgres;

--
-- Name: complaints_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.complaints_id_seq OWNED BY public.complaints.id;


--
-- Name: complaints_post_author_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.complaints_post_author_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.complaints_post_author_seq OWNER TO postgres;

--
-- Name: complaints_post_author_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.complaints_post_author_seq OWNED BY public.complaints.post_author;


--
-- Name: complaints_post_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.complaints_post_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.complaints_post_id_seq OWNER TO postgres;

--
-- Name: complaints_post_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.complaints_post_id_seq OWNED BY public.complaints.post_id;


--
-- Name: confirmation_tokens; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.confirmation_tokens (
    id bigint NOT NULL,
    token uuid,
    created timestamp without time zone,
    user_id bigint
);


ALTER TABLE public.confirmation_tokens OWNER TO postgres;

--
-- Name: confirmation_tokens_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.confirmation_tokens_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.confirmation_tokens_id_seq OWNER TO postgres;

--
-- Name: confirmation_tokens_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.confirmation_tokens_id_seq OWNED BY public.confirmation_tokens.id;


--
-- Name: currencies; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.currencies (
    id bigint NOT NULL,
    name character varying(100),
    ticker character varying(20),
    description text
);


ALTER TABLE public.currencies OWNER TO postgres;

--
-- Name: currencies_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.currencies_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.currencies_id_seq OWNER TO postgres;

--
-- Name: currencies_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.currencies_id_seq OWNED BY public.currencies.id;


--
-- Name: flyway_schema_history; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.flyway_schema_history (
    installed_rank integer NOT NULL,
    version character varying(50),
    description character varying(200) NOT NULL,
    type character varying(20) NOT NULL,
    script character varying(1000) NOT NULL,
    checksum integer,
    installed_by character varying(100) NOT NULL,
    installed_on timestamp without time zone DEFAULT now() NOT NULL,
    execution_time integer NOT NULL,
    success boolean NOT NULL
);


ALTER TABLE public.flyway_schema_history OWNER TO postgres;

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO postgres;

--
-- Name: likes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.likes (
    id bigint NOT NULL,
    user_id bigint NOT NULL,
    post_type bigint NOT NULL,
    post_id bigint NOT NULL,
    date timestamp without time zone NOT NULL
);


ALTER TABLE public.likes OWNER TO postgres;

--
-- Name: likes_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.likes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.likes_id_seq OWNER TO postgres;

--
-- Name: likes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.likes_id_seq OWNED BY public.likes.id;


--
-- Name: likes_post_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.likes_post_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.likes_post_id_seq OWNER TO postgres;

--
-- Name: likes_post_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.likes_post_id_seq OWNED BY public.likes.post_id;


--
-- Name: likes_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.likes_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.likes_user_id_seq OWNER TO postgres;

--
-- Name: likes_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.likes_user_id_seq OWNED BY public.likes.user_id;


--
-- Name: news; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.news (
    id bigint NOT NULL,
    created_by bigint,
    title character varying(10000),
    description character varying(50000),
    image_url character varying(2000),
    content text,
    tags character varying(5000)[],
    source character varying(1000),
    created timestamp without time zone,
    updated timestamp without time zone
);


ALTER TABLE public.news OWNER TO postgres;

--
-- Name: news_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.news_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.news_id_seq OWNER TO postgres;

--
-- Name: news_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.news_id_seq OWNED BY public.news.id;


--
-- Name: publications; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.publications (
    id bigint NOT NULL,
    created_by bigint,
    content text,
    tickers character varying(5000)[],
    created timestamp without time zone,
    updated timestamp without time zone
);


ALTER TABLE public.publications OWNER TO postgres;

--
-- Name: publications_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.publications_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.publications_id_seq OWNER TO postgres;

--
-- Name: publications_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.publications_id_seq OWNED BY public.publications.id;


--
-- Name: roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.roles (
    id integer NOT NULL,
    name character varying(20)
);


ALTER TABLE public.roles OWNER TO postgres;

--
-- Name: roles_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.roles_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.roles_id_seq OWNER TO postgres;

--
-- Name: roles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.roles_id_seq OWNED BY public.roles.id;


--
-- Name: user_reading_list; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_reading_list (
    user_id bigint NOT NULL,
    news_id bigint NOT NULL
);


ALTER TABLE public.user_reading_list OWNER TO postgres;

--
-- Name: user_roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_roles (
    user_id bigint NOT NULL,
    role_id integer NOT NULL
);


ALTER TABLE public.user_roles OWNER TO postgres;

--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id bigint NOT NULL,
    username character varying(20),
    email character varying(50),
    password character varying(120),
    is_enabled boolean,
    is_account_non_expired boolean,
    is_account_non_locked boolean,
    is_credentials_non_expired boolean
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- Name: comments id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comments ALTER COLUMN id SET DEFAULT nextval('public.comments_id_seq'::regclass);


--
-- Name: comments post_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comments ALTER COLUMN post_id SET DEFAULT nextval('public.comments_post_id_seq'::regclass);


--
-- Name: complaints id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.complaints ALTER COLUMN id SET DEFAULT nextval('public.complaints_id_seq'::regclass);


--
-- Name: complaints post_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.complaints ALTER COLUMN post_id SET DEFAULT nextval('public.complaints_post_id_seq'::regclass);


--
-- Name: complaints post_author; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.complaints ALTER COLUMN post_author SET DEFAULT nextval('public.complaints_post_author_seq'::regclass);


--
-- Name: confirmation_tokens id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.confirmation_tokens ALTER COLUMN id SET DEFAULT nextval('public.confirmation_tokens_id_seq'::regclass);


--
-- Name: currencies id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.currencies ALTER COLUMN id SET DEFAULT nextval('public.currencies_id_seq'::regclass);


--
-- Name: likes id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.likes ALTER COLUMN id SET DEFAULT nextval('public.likes_id_seq'::regclass);


--
-- Name: likes user_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.likes ALTER COLUMN user_id SET DEFAULT nextval('public.likes_user_id_seq'::regclass);


--
-- Name: likes post_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.likes ALTER COLUMN post_id SET DEFAULT nextval('public.likes_post_id_seq'::regclass);


--
-- Name: news id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.news ALTER COLUMN id SET DEFAULT nextval('public.news_id_seq'::regclass);


--
-- Name: publications id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.publications ALTER COLUMN id SET DEFAULT nextval('public.publications_id_seq'::regclass);


--
-- Name: roles id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles ALTER COLUMN id SET DEFAULT nextval('public.roles_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- Data for Name: comments; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.comments (id, user_id, post_type, post_id, message, created, updated) FROM stdin;
1	2	0	1	lol now	2021-02-10 13:54:04.079	\N
2	2	0	1	lol now	2021-02-10 13:58:40.521	\N
3	2	0	2	comment from Artur I guess	2021-02-10 13:59:04.938	\N
4	2	0	3	yes I think $Btc will work	2021-02-10 13:59:21.631	\N
5	2	0	4	$XEM does worth it	2021-02-10 13:59:34.53	\N
6	2	2	1	it's a great news!	2021-02-10 14:00:36.148	\N
7	2	2	1	yes I think so	2021-02-10 14:00:48.662	\N
8	2	2	2	wow	2021-02-10 14:00:54.233	\N
9	2	0	19	wow	2021-02-10 14:01:11.43	\N
10	2	0	19	ok why not	2021-02-10 14:01:15.307	\N
11	2	0	19	hahaha I think so	2021-02-10 14:01:22	\N
12	3	0	19	let's see, looks promising	2021-02-10 14:03:18.519	\N
13	3	0	1	let's see, looks promising	2021-02-10 14:03:25.712	\N
14	3	0	1	hmm yes why not ok	2021-02-10 14:03:33.34	\N
15	3	0	2	damn it gonna blast	2021-02-10 14:03:41.172	\N
16	3	0	3	just comment lol	2021-02-10 14:03:48.097	\N
17	3	0	3	just another comment lol	2021-02-10 14:03:51.094	\N
18	3	0	3	I love this comment system	2021-02-10 14:03:58.44	\N
19	3	0	5	wtf man 	2021-02-10 14:04:04.234	\N
20	3	0	5	wtf man :))))))	2021-02-10 14:04:07.819	\N
21	4	0	5	ok it twill work	2021-02-10 14:04:36.533	\N
22	4	0	5	hahaha yes	2021-02-10 14:04:43.244	\N
23	4	0	2	да, почему нет	2021-02-10 14:04:50.253	\N
24	4	0	2	я лох	2021-02-10 14:04:54.162	\N
25	4	0	2	ыыыыы	2021-02-10 14:04:58.948	\N
26	4	0	1	)))))0000	2021-02-10 14:05:04.109	\N
27	4	0	19	азахха )))))0000	2021-02-10 14:05:08.826	\N
28	4	2	1	азахха )))))0000	2021-02-10 14:05:18.609	\N
29	4	2	2	азахха )))))0000	2021-02-10 14:05:21.788	\N
30	4	2	3	да это рофло коммент	2021-02-10 14:05:30.727	\N
31	4	2	2	двач рулит	2021-02-10 14:05:35.497	\N
\.


--
-- Data for Name: complaints; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.complaints (id, user_id, post_type, post_id, post_author, reason, message, created) FROM stdin;
\.


--
-- Data for Name: confirmation_tokens; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.confirmation_tokens (id, token, created, user_id) FROM stdin;
\.


--
-- Data for Name: currencies; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.currencies (id, name, ticker, description) FROM stdin;
1	Bitcoin	BTC	Bitcoin
2	Ethereum	ETH	Ethereum
3	Tether	USDT	Tether
4	Cardano	ADA	Cardano
5	XRP	XRP	XRP
6	Polkadot	DOT	Polkadot
7	Binance Coin	BNB	Binance Coin
8	Litecoin	LTC	Litecoin
9	Chainlink	LINK	Chainlink
10	Dogecoin	DOGE	Dogecoin
11	Bitcoin Cash	BCH	Bitcoin Cash
12	Stellar	XLM	Stellar
13	USD Coin	USDC	USD Coin
14	Aave	AAVE	Aave
15	Uniswap	UNI	Uniswap
16	Wrapped Bitcoin	WBTC	Wrapped Bitcoin
17	Avalanche	AVAX	Avalanche
18	Bitcoin SV	BSV	Bitcoin SV
19	EOS	EOS	EOS
20	TRON	TRX	TRON
21	NEM	XEM	NEM
22	Elrond	EGLD	Elrond
23	Cosmos	ATOM	Cosmos
24	Monero	XMR	Monero
25	Terra	LUNA	Terra
26	Synthetix	SNX	Synthetix
27	Tezos	XTZ	Tezos
28	THETA	THETA	THETA
29	Huobi Token	HT	Huobi Token
30	Maker	MKR	Maker
31	Neo	NEO	Neo
32	VeChain	VET	VeChain
33	Compound	COMP	Compound
34	IOTA	MIOTA	IOTA
35	Solana	SOL	Solana
36	Filecoin	FIL	Filecoin
37	Dai	DAI	Dai
38	FTX Token	FTT	FTX Token
39	SushiSwap	SUSHI	SushiSwap
40	Binance USD	BUSD	Binance USD
41	Crypto.com Coin	CRO	Crypto.com Coin
42	UMA	UMA	UMA
43	Celsius	CEL	Celsius
44	UNUS SED LEO	LEO	UNUS SED LEO
45	The Graph	GRT	The Graph
46	Dash	DASH	Dash
47	Algorand	ALGO	Algorand
48	yearn.finance	YFI	yearn.finance
49	Zcash	ZEC	Zcash
50	Decred	DCR	Decred
51	Ethereum Classic	ETC	Ethereum Classic
52	0x	ZRX	0x
53	BitTorrent	BTT	BitTorrent
54	Kusama	KSM	Kusama
55	Zilliqa	ZIL	Zilliqa
56	Revain	REV	Revain
57	Waves	WAVES	Waves
58	Nexo	NEXO	Nexo
59	Celo	CELO	Celo
60	NEAR Protocol	NEAR	NEAR Protocol
61	Ren	REN	Ren
62	Loopring	LRC	Loopring
63	renBTC	RENBTC	renBTC
64	SwissBorg	CHSB	SwissBorg
65	Hedera Hashgraph	HBAR	Hedera Hashgraph
66	OMG Network	OMG	OMG Network
67	PancakeSwap	CAKE	PancakeSwap
68	Ontology	ONT	Ontology
69	THORChain	RUNE	THORChain
70	Paxos Standard	PAX	Paxos Standard
71	Qtum	QTUM	Qtum
72	DigiByte	DGB	DigiByte
73	Curve DAO Token	CRV	Curve DAO Token
74	Matic Network	MATIC	Matic Network
75	Basic Attention Token	BAT	Basic Attention Token
76	1inch	1INCH	1inch
77	Nano	NANO	Nano
78	Voyager Token	VGX	Voyager Token
79	OKB	OKB	OKB
80	ICON	ICX	ICON
81	Reserve Rights	RSR	Reserve Rights
82	Quant	QNT	Quant
83	Siacoin	SC	Siacoin
84	HUSD	HUSD	HUSD
85	HedgeTrade	HEDG	HedgeTrade
86	Horizen	ZEN	Horizen
87	Kyber Network	KNC	Kyber Network
88	Bitcoin BEP2	BTCB	Bitcoin BEP2
89	Ampleforth	AMPL	Ampleforth
90	Stacks	STX	Stacks
91	TrueUSD	TUSD	TrueUSD
92	Ravencoin	RVN	Ravencoin
93	Decentraland	MANA	Decentraland
94	IOST	IOST	IOST
95	Alpha Finance Lab	ALPHA	Alpha Finance Lab
96	Energy Web Token	EWT	Energy Web Token
97	Venus	XVS	Venus
98	Fantom	FTM	Fantom
99	Enjin Coin	ENJ	Enjin Coin
100	Ocean Protocol	OCEAN	Ocean Protocol
101	Bancor	BNT	Bancor
102	Verge	XVG	Verge
103	TerraUSD	UST	TerraUSD
104	Lisk	LSK	Lisk
105	Balancer	BAL	Balancer
106	Band Protocol	BAND	Band Protocol
107	Augur	REP	Augur
108	Flow (Dapper Labs)	FLOW	Flow (Dapper Labs)
109	Helium	HNT	Helium
110	Status	SNT	Status
111	Bitcoin Gold	BTG	Bitcoin Gold
112	FunFair	FUN	FunFair
113	KuCoin Token	KCS	KuCoin Token
114	Kava.io	KAVA	Kava.io
115	Injective Protocol	INJ	Injective Protocol
116	Arweave	AR	Arweave
117	Aragon	ANT	Aragon
118	Theta Fuel	TFUEL	Theta Fuel
119	NuCypher	NU	NuCypher
120	Numeraire	NMR	Numeraire
121	Pundi X	NPXS	Pundi X
122	Swipe	SXP	Swipe
123	Gnosis	GNO	Gnosis
124	Serum	SRM	Serum
125	Nervos Network	CKB	Nervos Network
126	Holo	HOT	Holo
127	RSK Infrastructure Framework	RIF	RSK Infrastructure Framework
128	Golem	GLM	Golem
129	Fetch.ai	FET	Fetch.ai
130	ZB Token	ZB	ZB Token
131	Telcoin	TEL	Telcoin
132	Harmony	ONE	Harmony
133	Orchid	OXT	Orchid
134	MaidSafeCoin	MAID	MaidSafeCoin
135	Civic	CVC	Civic
136	Morpheus.Network	MRPH	Morpheus.Network
137	Secret	SCRT	Secret
138	IoTeX	IOTX	IoTeX
139	Chiliz	CHZ	Chiliz
140	Vai	VAI	Vai
141	BitShares	BTS	BitShares
142	Bitcoin Diamond	BCD	Bitcoin Diamond
143	Utrust	UTK	Utrust
144	Oasis Network	ROSE	Oasis Network
145	TomoChain	TOMO	TomoChain
146	Storj	STORJ	Storj
147	Mainframe	MFT	Mainframe
148	MonaCoin	MONA	MonaCoin
149	ABBC Coin	ABBC	ABBC Coin
150	OriginTrail	TRAC	OriginTrail
151	iExec RLC	RLC	iExec RLC
152	Bytom	BTM	Bytom
153	Stratis	STRAX	Stratis
154	PAX Gold	PAXG	PAX Gold
155	Unibright	UBT	Unibright
156	Ankr	ANKR	Ankr
157	district0x	DNT	district0x
158	CyberVein	CVT	CyberVein
159	aelf	ELF	aelf
160	Akropolis	AKRO	Akropolis
161	Gemini Dollar	GUSD	Gemini Dollar
162	SingularityNET	AGI	SingularityNET
163	Ardor	ARDR	Ardor
164	Haven Protocol	XHV	Haven Protocol
165	Steem	STEEM	Steem
166	xDai	STAKE	xDai
167	Komodo	KMD	Komodo
168	Reef	REEF	Reef
169	Wanchain	WAN	Wanchain
170	JUST	JST	JUST
171	ReddCoin	RDD	ReddCoin
172	TrustSwap	SWAP	TrustSwap
173	MX Token	MX	MX Token
174	IRISnet	IRIS	IRISnet
175	saffron.finance	SFI	saffron.finance
176	Tellor	TRB	Tellor
177	Populous	PPT	Populous
178	DFI.Money	YFII	DFI.Money
179	Velas	VLX	Velas
180	Wrapped NXM	WNXM	Wrapped NXM
181	Kin	KIN	Kin
182	VeThor Token	VTHO	VeThor Token
183	Aidos Kuneen	ADK	Aidos Kuneen
184	Origin Protocol	OGN	Origin Protocol
185	Travala.com	AVA	Travala.com
186	Energi	NRG	Energi
187	LTO Network	LTO	LTO Network
188	Polymath	POLY	Polymath
189	Celer Network	CELR	Celer Network
190	COTI	COTI	COTI
191	Unifi Protocol DAO	UNFI	Unifi Protocol DAO
192	Divi	DIVI	Divi
193	Syntropy	NOIA	Syntropy
194	bZx Protocol	BZRX	bZx Protocol
195	Hive	HIVE	Hive
196	DIA	DIA	DIA
197	Ark	ARK	Ark
198	StormX	STMX	StormX
199	SUN	SUN	SUN
200	CertiK	CTK	CertiK
201	HEX	HEX	HEX
202	Counos X	CCXX	Counos X
203	INO COIN	INO	INO COIN
204	DeFiChain	DFI	DeFiChain
205	ThoreCoin	THR	ThoreCoin
206	Creditcoin	CTC	Creditcoin
207	Insight Chain	INB	Insight Chain
208	botXcoin	BOTX	botXcoin
209	IZE	IZE	IZE
210	NXM	NXM	NXM
211	Wrapped BNB	WBNB	Wrapped BNB
212	Bridge Oracle	BRG	Bridge Oracle
213	GNY	GNY	GNY
214	Zelwin	ZLW	Zelwin
215	Strong	STRONG	Strong
216	Bitcoin Cash ABC	BCHA	Bitcoin Cash ABC
217	SafePal	SFP	SafePal
218	Cipher Core Token	CIPHC	Cipher Core Token
219	DerivaDAO	DDX	DerivaDAO
220	Bitpanda Ecosystem Token	BEST	Bitpanda Ecosystem Token
221	stETH (Lido)	STETH	stETH (Lido)
222	Basid Coin	BASID	Basid Coin
223	Huobi BTC	HBTC	Huobi BTC
224	Venus BNB	vBNB	Venus BNB
225	WhiteCoin	XWC	WhiteCoin
226	Badger DAO	BADGER	Badger DAO
227	Venus BTC	vBTC	Venus BTC
228	The Transfer Token	TTT	The Transfer Token
229	Keep Network	KEEP	Keep Network
230	Largo Coin	LRG	Largo Coin
231	Linear	LINA	Linear
232	BitMax Token	BTMX	BitMax Token
233	Elitium	EUM	Elitium
234	Sologenic	SOLO	Sologenic
235	Trust Wallet Token	TWT	Trust Wallet Token
236	Mirror Protocol	MIR	Mirror Protocol
237	Perpetual Protocol	PERP	Perpetual Protocol
238	Amp	AMP	Amp
239	Venus XVS	vXVS	Venus XVS
240	MimbleWimbleCoin	MWC	MimbleWimbleCoin
241	ZKSwap	ZKS	ZKSwap
242	Doctors Coin	DRS	Doctors Coin
243	Eauric	EAURIC	Eauric
244	YUSRA	YUSRA	YUSRA
245	Cream Finance	CREAM	Cream Finance
246	Neutrino USD	USDN	Neutrino USD
247	sUSD	SUSD	sUSD
248	Harvest Finance	FARM	Harvest Finance
249	Hegic	HEGIC	Hegic
250	Polkastarter	POLS	Polkastarter
251	Idea Chain Coin	ICH	Idea Chain Coin
252	PARSIQ	PRQ	PARSIQ
253	SKALE Network	SKL	SKALE Network
254	Gala	GALA	Gala
255	Litentry	LIT	Litentry
256	Mobilian Coin	MBN	Mobilian Coin
257	NewYork Exchange	NYE	NewYork Exchange
258	Akash Network	AKT	Akash Network
259	Bloomzed Loyalty Club Ticket	BLCT	Bloomzed Loyalty Club Ticket
260	Edgeware	EDG	Edgeware
261	Hellenic Coin	HNC	Hellenic Coin
262	XinFin Network	XDC	XinFin Network
263	GreenPower	GRN	GreenPower
264	Vitae	VITAE	Vitae
265	Cashaa	CAS	Cashaa
266	Massnet	MASS	Massnet
267	XeniosCoin	XNC	XeniosCoin
268	Bytecoin	BCN	Bytecoin
269	Orion Protocol	ORN	Orion Protocol
270	Chimpion	BNANA	Chimpion
271	DIGG	DIGG	DIGG
272	The Sandbox	SAND	The Sandbox
273	MATH	MATH	MATH
274	Beefy.Finance	BIFI	Beefy.Finance
275	Mixin	XIN	Mixin
276	Sapphire	SAPP	Sapphire
277	Folgory Coin	FLG	Folgory Coin
278	DuckDaoDime	DDIM	DuckDaoDime
279	NEST Protocol	NEST	NEST Protocol
280	Empty Set Dollar	ESD	Empty Set Dollar
281	Frax	FRAX	Frax
282	HARD Protocol	HARD	HARD Protocol
283	Beldex	BDX	Beldex
284	TNC Coin	TNC	TNC Coin
285	API3	API3	API3
286	CoinMetro Token	XCM	CoinMetro Token
287	AXEL	AXEL	AXEL
288	Spartan Protocol	SPARTA	Spartan Protocol
289	Poseidon Network	QQQ	Poseidon Network
290	WAX	WAXP	WAX
291	Syscoin	SYS	Syscoin
292	Livepeer	LPT	Livepeer
293	Qcash	QC	Qcash
294	BarnBridge	BOND	BarnBridge
295	Enzyme	MLN	Enzyme
296	Metacoin	MTC	Metacoin
297	Keep3rV1	KP3R	Keep3rV1
298	Venus ETH	vETH	Venus ETH
299	DODO	DODO	DODO
300	Opium	OPIUM	Opium
301	Rakon	RKN	Rakon
302	GateToken	GT	GateToken
303	AdEx Network	ADX	AdEx Network
304	Attila	ATT	Attila
305	Sport and Leisure	SNL	Sport and Leisure
306	Rari Governance Token	RGT	Rari Governance Token
307	Loom Network	LOOM	Loom Network
308	LBRY Credits	LBC	LBRY Credits
309	Sora	XOR	Sora
310	Handshake 	HNS	Handshake 
311	Atari Token	ATRI	Atari Token
312	Zenon	ZNN	Zenon
313	Meta	MTA	Meta
314	Elastos	ELA	Elastos
315	AllianceBlock	ALBT	AllianceBlock
316	Ultra	UOS	Ultra
317	DxChain Token	DX	DxChain Token
318	NFTX	NFTX	NFTX
319	Uquid Coin	UQC	Uquid Coin
320	KardiaChain	KAI	KardiaChain
321	WINk	WIN	WINk
322	Electroneum	ETN	Electroneum
323	PAID Network	PAID	PAID Network
324	Power Ledger	POWR	Power Ledger
325	BakeryToken	BAKE	BakeryToken
326	Flamingo	FLM	Flamingo
327	Hxro	HXRO	Hxro
328	SparkPoint	SRK	SparkPoint
329	Dent	DENT	Dent
330	Darma Cash	DMCH	Darma Cash
331	TROY	TROY	TROY
332	QuarkChain	QKC	QuarkChain
333	WaykiChain	WICC	WaykiChain
334	Bella Protocol	BEL	Bella Protocol
335	Request	REQ	Request
336	EFFORCE	WOZX	EFFORCE
337	Firo	FIRO	Firo
338	STASIS EURO	EURS	STASIS EURO
339	PowerPool	CVP	PowerPool
340	Everipedia	IQ	Everipedia
341	Ergo	ERG	Ergo
342	Rocket Pool	RPL	Rocket Pool
343	Axie Infinity	AXS	Axie Infinity
344	Tornado Cash	TORN	Tornado Cash
345	TrueFi	TRU	TrueFi
346	Streamr	DATA	Streamr
347	MINDOL	MIN	MINDOL
348	Venus BUSD	vBUSD	Venus BUSD
349	cVault.finance	CORE	cVault.finance
350	Tokenlon Network Token	LON	Tokenlon Network Token
351	Etherisc DIP Token	DIP	Etherisc DIP Token
352	Ferrum Network	FRM	Ferrum Network
353	BTU Protocol	BTU	BTU Protocol
354	ankrETH	aEth	ankrETH
355	Rewardiqa	REW	Rewardiqa
356	Dragonchain	DRGN	Dragonchain
357	DAO Maker	DAO	DAO Maker
358	Aion	AION	Aion
359	MXC	MXC	MXC
360	pNetwork	PNT	pNetwork
361	Bluzelle	BLZ	Bluzelle
362	Huobi Pool Token	HPT	Huobi Pool Token
363	CryptalDash	CRD	CryptalDash
364	NULS	NULS	NULS
365	HyperCash	HC	HyperCash
366	Kleros	PNK	Kleros
367	ChainX	PCX	ChainX
368	Stakenet	XSN	Stakenet
369	Waves Enterprise	WEST	Waves Enterprise
370	yOUcash	YOUC	yOUcash
371	ShareToken	SHR	ShareToken
372	The Midas Touch Gold	TMTG	The Midas Touch Gold
373	OctoFi	OCTO	OctoFi
374	88mph	MPH	88mph
375	MCO	MCO	MCO
376	Phala.Network	PHA	Phala.Network
377	COVER Protocol	COVER	COVER Protocol
378	Beam	BEAM	Beam
379	Metal	MTL	Metal
380	Centrality	CENNZ	Centrality
381	Waltonchain	WTC	Waltonchain
382	WazirX	WRX	WazirX
383	SOLVE	SOLVE	SOLVE
384	Audius	AUDIO	Audius
385	RAMP	RAMP	RAMP
386	Duck DAO (DLP Duck Token)	DUCK	Duck DAO (DLP Duck Token)
387	Burger Swap	BURGER	Burger Swap
388	Darwinia Network	RING	Darwinia Network
389	STEM CELL COIN	SCC	STEM CELL COIN
390	v.systems	VSYS	v.systems
391	Bounce Token	BOT	Bounce Token
392	MAPS	MAPS	MAPS
393	MANTRA DAO	OM	MANTRA DAO
394	Bithao	BHAO	Bithao
395	Bao Finance	BAO	Bao Finance
396	Nexus	NXS	Nexus
397	0Chain	ZCN	0Chain
398	mStable USD	MUSD	mStable USD
399	Marlin	POND	Marlin
400	DigixDAO	DGD	DigixDAO
401	Nimiq	NIM	Nimiq
402	Aeternity	AE	Aeternity
403	Homeros	HMR	Homeros
404	Bitball Treasure	BTRS	Bitball Treasure
405	TitanSwap	TITAN	TitanSwap
406	Rubic	RBC	Rubic
407	Bondly	BONDLY	Bondly
408	BitcoinPoS	BPS	BitcoinPoS
409	Orbs	ORBS	Orbs
410	Orbit Chain	ORC	Orbit Chain
411	Cindicator	CND	Cindicator
412	MVL	MVL	MVL
413	PIVX	PIVX	PIVX
414	Cortex	CTXC	Cortex
415	Ignis	IGNIS	Ignis
416	IDEX	IDEX	IDEX
417	Dusk Network	DUSK	Dusk Network
418	USDX [Kava]	USDX	USDX [Kava]
419	Ripio Credit Network	RCN	Ripio Credit Network
420	Groestlcoin	GRS	Groestlcoin
421	Measurable Data Token	MDT	Measurable Data Token
422	Fusion	FSN	Fusion
423	DAD	DAD	DAD
424	Contentos	COS	Contentos
425	Veritaseum	VERI	Veritaseum
426	Oxen	LOKI	Oxen
427	inSure DeFi	SURE	inSure DeFi
428	TerraKRW	KRT	TerraKRW
429	Prometeus	PROM	Prometeus
430	Pickle Finance	PICKLE	Pickle Finance
431	Shopping	SPI	Shopping
432	JulSwap	JULD	JulSwap
433	Frax Share	FXS	Frax Share
434	ARPA Chain	ARPA	ARPA Chain
435	Whiteheart	WHITE	Whiteheart
436	Bankera	BNK	Bankera
437	Cartesi	CTSI	Cartesi
438	Celo Dollar	CUSD	Celo Dollar
439	AirSwap	AST	AirSwap
440	Thunder Token	TT	Thunder Token
441	Neutrino Token	NSBT	Neutrino Token
442	BOSAGORA	BOA	BOSAGORA
443	GXChain	GXC	GXChain
444	Decentr	DEC	Decentr
445	LGCY Network	LGCY	LGCY Network
446	BoringDAO	BOR	BoringDAO
447	Lambda	LAMB	Lambda
448	Super Zero Protocol	SERO	Super Zero Protocol
449	Klever	KLV	Klever
450	Quantstamp	QSP	Quantstamp
451	Switcheo	SWTH	Switcheo
452	Kadena	KDA	Kadena
453	Neblio	NEBL	Neblio
454	Ducato Protocol Token	DUCATO	Ducato Protocol Token
455	Gas	GAS	Gas
456	1irstcoin	FST	1irstcoin
457	Zap	ZAP	Zap
458	Wirex Token	WXT	Wirex Token
459	Venus USDT	vUSDT	Venus USDT
460	Hermez Network	HEZ	Hermez Network
461	Grin	GRIN	Grin
462	Ultiledger	ULT	Ultiledger
463	Perlin	PERL	Perlin
464	Aleph.im	ALEPH	Aleph.im
465	DEXTools	DEXT	DEXTools
466	SALT	SALT	SALT
467	Tixl [NEW]	TXL	Tixl [NEW]
468	Metronome	MET	Metronome
469	Basis Cash	BAC	Basis Cash
470	Presearch	PRE	Presearch
471	dForce	DF	dForce
472	Wing	WING	Wing
473	Robonomics.network	XRT	Robonomics.network
474	USDK	USDK	USDK
475	Sentivate	SNTVT	Sentivate
476	Nexalt	XLT	Nexalt
477	Pirate Chain	ARRR	Pirate Chain
478	BORA	BORA	BORA
479	NKN	NKN	NKN
480	Benchmark Protocol	MARK	Benchmark Protocol
481	Gleec	GLEEC	Gleec
482	Decentralized Vulnerability Platform	DVP	Decentralized Vulnerability Platform
483	FIO Protocol	FIO	FIO Protocol
484	Cocos-BCX	COCOS	Cocos-BCX
485	RSK Smart Bitcoin	RBTC	RSK Smart Bitcoin
486	Tixl [old]	MTXLT	Tixl [old]
487	OptionRoom	ROOM	OptionRoom
488	Frontier	FRONT	Frontier
489	Carry	CRE	Carry
490	PEAKDEFI	PEAK	PEAKDEFI
491	Metaverse Dualchain Network Architecture	DNA	Metaverse Dualchain Network Architecture
492	VerusCoin	VRSC	VerusCoin
493	Nebulas	NAS	Nebulas
494	EasyFi	EASY	EasyFi
495	Nash Exchange	NEX	Nash Exchange
496	dHedge DAO	DHT	dHedge DAO
497	BnkToTheFuture	BFT	BnkToTheFuture
498	Einsteinium	EMC2	Einsteinium
499	Quantum Resistant Ledger	QRL	Quantum Resistant Ledger
500	DREP	DREP	DREP
501	Bonfida	FIDA	Bonfida
502	VIDT Datalink	VIDT	VIDT Datalink
503	Safex Token	SFT	Safex Token
504	Navcoin	NAV	Navcoin
505	Standard Tokenization Protocol	STPT	Standard Tokenization Protocol
506	12Ships	TSHP	12Ships
507	Propy	PRO	Propy
508	BASIC	BASIC	BASIC
509	Leverj Gluon	L2	Leverj Gluon
510	Proton	XPR	Proton
511	Steem Dollars	SBD	Steem Dollars
512	Counos Coin	CCA	Counos Coin
513	Chromia	CHR	Chromia
514	SnowSwap	SNOW	SnowSwap
515	Constellation	DAG	Constellation
516	Function X	FX	Function X
517	Obyte	GBYTE	Obyte
518	GoChain	GO	GoChain
519	Xensor	XSR	Xensor
520	Telos	TLOS	Telos
521	Refereum	RFR	Refereum
522	Meme	MEME	Meme
523	PRIZM	PZM	PRIZM
524	Nxt	NXT	Nxt
525	Spendcoin	SPND	Spendcoin
526	ASTA	ASTA	ASTA
527	Phoenix Global	PHB	Phoenix Global
528	Furucombo	COMBO	Furucombo
529	Render Token	RNDR	Render Token
530	Aavegotchi	GHST	Aavegotchi
531	Skycoin	SKY	Skycoin
532	Vertcoin	VTC	Vertcoin
533	APY.Finance	APY	APY.Finance
534	Rio DeFi	RFUEL	Rio DeFi
535	BitcoinHD	BHD	BitcoinHD
536	Anyswap	ANY	Anyswap
537	Multiplier	MXX	Multiplier
538	StableXSwap	STAX	StableXSwap
539	Swingby	SWINGBY	Swingby
540	RChain	REV	RChain
541	NerveNetwork	NVT	NerveNetwork
542	SpaceChain	SPC	SpaceChain
543	Swerve	SWRV	Swerve
544	Lido DAO Token	LDO	Lido DAO Token
545	WOM Protocol	WOM	WOM Protocol
546	VITE	VITE	VITE
547	Crypterium	CRPT	Crypterium
548	Global Digital Content	GDC	Global Digital Content
549	Oraichain Token	ORAI	Oraichain Token
550	Factom	FCT	Factom
551	QASH	QASH	QASH
552	Aurora	AOA	Aurora
553	Selfkey	KEY	Selfkey
554	KeeperDAO	ROOK	KeeperDAO
555	REVV	REVV	REVV
556	PAC Global	PAC	PAC Global
557	Venus SXP	vSXP	Venus SXP
558	ForTube	FOR	ForTube
559	Namecoin	NMC	Namecoin
560	AppCoins	APPC	AppCoins
561	Anchor Neural World	ANW	Anchor Neural World
562	YF Link	YFL	YF Link
563	Raiden Network Token	RDN	Raiden Network Token
564	Wootrade	WOO	Wootrade
565	Aergo	AERGO	Aergo
566	FC Barcelona Fan Token	BAR	FC Barcelona Fan Token
567	ZEON	ZEON	ZEON
568	Galatasaray Fan Token	GAL	Galatasaray Fan Token
569	Zynecoin	ZYN	Zynecoin
570	JustLiquidity	JUL	JustLiquidity
571	UniLend	UFT	UniLend
572	YFDAI.FINANCE	YF-DAI	YFDAI.FINANCE
573	Moeda Loyalty Points	MDA	Moeda Loyalty Points
574	Hacken Token	HAI	Hacken Token
575	Viacoin	VIA	Viacoin
576	Rally	RLY	Rally
577	BitKan	KAN	BitKan
578	Poolz Finance	POOLZ	Poolz Finance
579	SUKU	SUKU	SUKU
580	ARMOR	ARMOR	ARMOR
581	Venus LINK	vLINK	Venus LINK
582	Dynamic Trading Rights	DTR	Dynamic Trading Rights
583	Dock	DOCK	Dock
584	DigitalBits	XDB	DigitalBits
585	Endor Protocol	EDR	Endor Protocol
586	SIRIN LABS Token	SRN	SIRIN LABS Token
587	GeoDB	GEO	GeoDB
588	Props Token	PROPS	Props Token
589	DMarket	DMT	DMarket
590	OAX	OAX	OAX
591	BigONE Token	ONE	BigONE Token
592	Mithril	MITH	Mithril
593	Bifrost	BFC	Bifrost
594	TrueChain	ИСТИНА	TrueChain
595	Invictus Hyperion Fund	IHF	Invictus Hyperion Fund
596	Decentral Games	DG	Decentral Games
597	Crust	CRU	Crust
598	Morpheus Labs	MITX	Morpheus Labs
599	Arcblock	ABT	Arcblock
600	Project Pai	PAI	Project Pai
601	Gifto	GTO	Gifto
602	LGO Token	LGO	LGO Token
603	Metaverse ETP	ETP	Metaverse ETP
604	King DAG	KDAG	King DAG
605	Hashgard	GARD	Hashgard
606	suterusu	SUTER	suterusu
607	Newscrypto	NWC	Newscrypto
608	Wabi	WABI	Wabi
609	USDJ	USDJ	USDJ
610	reflect.finance	RFI	reflect.finance
611	KLAYswap Protocol	KSP	KLAYswap Protocol
612	Sentinel Protocol	UPP	Sentinel Protocol
613	Genesis Vision	GVT	Genesis Vision
614	YIELD App	YLD	YIELD App
615	PlatonCoin	PLTC	PlatonCoin
616	Atomic Wallet Coin	AWC	Atomic Wallet Coin
617	Davinci Coin	DAC	Davinci Coin
618	MediBloc	MED	MediBloc
619	Venus LTC	vLTC	Venus LTC
620	Apollo Currency	APL	Apollo Currency
621	TenX	PAY	TenX
622	Molecular Future	MOF	Molecular Future
623	QLC Chain	QLC	QLC Chain
624	AMO Coin	AMO	AMO Coin
625	Universa	UTNP	Universa
626	LATOKEN	LA	LATOKEN
627	Idle	IDLE	Idle
628	VestChain	VEST	VestChain
629	Stafi	FIS	Stafi
630	Beowulf	BWF	Beowulf
631	Bread	BRD	Bread
632	Ambrosus	AMB	Ambrosus
633	VideoCoin	VID	VideoCoin
634	S4FE	S4F	S4FE
635	PLATINCOIN	PLC	PLATINCOIN
636	BIKI	BIKI	BIKI
637	Gem Exchange And Trading	GXT	Gem Exchange And Trading
638	BitForex Token	BF	BitForex Token
639	MovieBloc	MBL	MovieBloc
640	DOS Network	DOS	DOS Network
641	BHPCoin	BHP	BHPCoin
642	LockTrip	LOC	LockTrip
643	Base Protocol	BASE	Base Protocol
644	IQeon	IQN	IQeon
645	ThoreNext	THX	ThoreNext
646	DSLA Protocol	DSLA	DSLA Protocol
647	OST	OST	OST
648	Nucleus Vision	NCASH	Nucleus Vision
649	DeXe	DEXE	DeXe
650	Dego Finance	DEGO	Dego Finance
651	Emirex Token	EMRX	Emirex Token
652	LinkEye	LET	LinkEye
653	LUKSO	LYXe	LUKSO
654	MiL.k	MLK	MiL.k
655	Tachyon Protocol	IPX	Tachyon Protocol
656	Bitcoin 2	BTC2	Bitcoin 2
657	Humanscape	HUM	Humanscape
658	Archer DAO Governance Token	ARCH	Archer DAO Governance Token
659	Kcash	KCASH	Kcash
660	Santiment Network Token	SAN	Santiment Network Token
661	Peercoin	PPC	Peercoin
662	Moss Coin	MOC	Moss Coin
663	AnimalGo	GOM2	AnimalGo
664	Maro	MARO	Maro
665	PowerTrade Fuel	PTF	PowerTrade Fuel
666	Ruff	RUFF	Ruff
667	Indexed Finance	NDX	Indexed Finance
668	TokenClub	TCT	TokenClub
669	Metadium	META	Metadium
670	dKargo	DKA	dKargo
671	Dynamic	DYN	Dynamic
672	Juventus Fan Token	JUV	Juventus Fan Token
673	NEXT	NET	NEXT
674	Monolith	TKN	Monolith
675	Ubiq	UBQ	Ubiq
676	Paris Saint-Germain Fan Token	PSG	Paris Saint-Germain Fan Token
677	PumaPay	PMA	PumaPay
678	SmartCash	SMART	SmartCash
679	ZeroSwap	ZEE	ZeroSwap
680	Dero	DERO	Dero
681	PieDAO DOUGH v2	DOUGH	PieDAO DOUGH v2
682	Everex	EVX	Everex
683	CONUN	CON	CONUN
684	#MetaHash	MHC	#MetaHash
685	NIX	NIX	NIX
686	LCX	LCX	LCX
687	CoinEx Token	CET	CoinEx Token
688	Yield Optimization Platform & Protocol	YOP	Yield Optimization Platform & Protocol
689	DigitalNote	XDN	DigitalNote
690	Blox	CDT	Blox
691	Tap	XTP	Tap
692	LiquidApps	DAPP	LiquidApps
693	Bit-Z Token	BZ	Bit-Z Token
694	Sentinel	SENT	Sentinel
695	Levolution	LEVL	Levolution
696	Veros	VRS	Veros
697	Alchemy Pay	ACH	Alchemy Pay
698	StakeCubeCoin	SCC	StakeCubeCoin
699	PCHAIN	PI	PCHAIN
700	HUNT	HUNT	HUNT
701	Tokenomy	TEN	Tokenomy
702	CyberMiles	CMT	CyberMiles
703	Aryacoin	AYA	Aryacoin
704	WePower	WPR	WePower
705	smARTOFGIVING	AOG	smARTOFGIVING
706	ZBG Token	ZT	ZBG Token
707	Smart MFG	MFG	Smart MFG
708	Trittium	TRTT	Trittium
709	CUTcoin	CUT	CUTcoin
710	Hakka.Finance	HAKKA	Hakka.Finance
711	High Performance Blockchain	HPB	High Performance Blockchain
712	POA	POA	POA
713	Anchor	ANCT	Anchor
714	CUDOS	CUDOS	CUDOS
715	Gulden	NLG	Gulden
716	Particl	PART	Particl
717	Blocknet	BLOCK	Blocknet
718	Content Value Network	CVNT	Content Value Network
719	VIDY	VIDY	VIDY
720	LikeCoin	LIKE	LikeCoin
721	Observer	OBSR	Observer
722	Stake DAO	SDT	Stake DAO
723	Time New Bank	TNB	Time New Bank
724	carVertical	CV	carVertical
725	AGA Token	AGA	AGA Token
726	TE-FOOD	TONE	TE-FOOD
727	DeFi Yield Protocol	DYP	DeFi Yield Protocol
728	Prosper	PROS	Prosper
729	Validity	VAL	Validity
730	Geeq	GEEQ	Geeq
731	MahaDAO	MAHA	MahaDAO
732	Achain	ACT	Achain
733	DATA	DTA	DATA
734	Blockzero Labs	XIO	Blockzero Labs
735	SymVerse	SYM	SymVerse
736	Newton	NEW	Newton
737	FirmaChain	FCT	FirmaChain
738	Mettalex	MTLX	Mettalex
739	TOP	TOP	TOP
740	Unitrade	TRADE	Unitrade
741	Holyheld	HOLY	Holyheld
742	MixMarvel	MIX	MixMarvel
743	Bird.Money	BIRD	Bird.Money
744	PIXEL	PXL	PIXEL
745	Phantasma	SOUL	Phantasma
746	FOAM	FOAM	FOAM
747	Dawn Protocol	DAWN	Dawn Protocol
748	Agrello	DLT	Agrello
749	Tokamak Network	TON	Tokamak Network
750	Auctus	AUC	Auctus
751	TrustVerse	TRV	TrustVerse
752	OpenDAO	OPEN	OpenDAO
753	Bibox Token	BIX	Bibox Token
754	Hedget	HGET	Hedget
755	Casino Betting Coin	CBC	Casino Betting Coin
756	Crowns	CWS	Crowns
757	CanYaCoin	CAN	CanYaCoin
758	Mysterium	MYST	Mysterium
759	Nestree	EGG	Nestree
760	YOYOW	YOYOW	YOYOW
761	STATERA	STA	STATERA
762	Rarible	RARI	Rarible
763	Effect.AI	EFX	Effect.AI
764	Lamden	TAU	Lamden
765	Quiztok	QTCON	Quiztok
766	Opacity	OPCT	Opacity
767	RING X PLATFORM	RINGX	RING X PLATFORM
768	Viberate	VIB	Viberate
769	Wagerr	WGR	Wagerr
770	SwftCoin	SWFTC	SwftCoin
771	ROOBEE	ROOBEE	ROOBEE
772	DEX	DEX	DEX
773	Metrix Coin	MRX	Metrix Coin
774	Mooncoin	MOON	Mooncoin
775	EUNO	EUNO	EUNO
776	Filecash	FIC	Filecash
777	GameCredits	GAME	GameCredits
778	SaTT	SATT	SaTT
779	Aitra	AITRA	Aitra
780	Feathercoin	FTC	Feathercoin
781	GET Protocol	GET	GET Protocol
782	Kryll	KRL	Kryll
783	Elamachain	ELAMA	Elamachain
784	Realio Network	RIO	Realio Network
785	APIX	APIX	APIX
786	Venus DOT	vDOT	Venus DOT
787	FIBOS	FO	FIBOS
788	Burst	BURST	Burst
789	Bonded Finance	BOND	Bonded Finance
790	XMax	XMX	XMax
791	Monetha	MTH	Monetha
792	Swace	SWACE	Swace
793	ILCOIN	ILC	ILCOIN
794	FNB Protocol	FNB	FNB Protocol
795	Grid+	GRID	Grid+
796	Autonio	NIOX	Autonio
797	X-CASH	XCASH	X-CASH
798	Digitex Token	DGTX	Digitex Token
799	CWV Chain	CWV	CWV Chain
800	Eminer	EM	Eminer
801	Jibrel Network	JNT	Jibrel Network
802	Cobak Token	CBK	Cobak Token
803	UniLayer	LAYER	UniLayer
804	BLOCKv	VEE	BLOCKv
805	PotCoin	POT	PotCoin
806	CargoX	CXO	CargoX
807	GoCrypto Token	GOC	GoCrypto Token
808	BIZZCOIN	BIZZ	BIZZCOIN
809	BTSE	BTSE	BTSE
810	Polis	POLIS	Polis
811	Myriad	XMY	Myriad
812	ASKO	ASKO	ASKO
813	Seele-N	SEELE	Seele-N
814	Noku	NOKU	Noku
815	BuySell	BULL	BuySell
816	Atletico De Madrid Fan Token	ATM	Atletico De Madrid Fan Token
817	42-coin	42	42-coin
818	IoT Chain	ITC	IoT Chain
819	Valobit	VBIT	Valobit
820	Quasarcoin	QAC	Quasarcoin
821	SIX	SIX	SIX
822	Kira Network	KEX	Kira Network
823	Mirrored Apple	mAAPL	Mirrored Apple
824	Alpha Quark Token	AQT	Alpha Quark Token
825	Centaur	CNTR	Centaur
826	nDEX	NDX	nDEX
827	SmartCredit Token	SMARTCREDIT	SmartCredit Token
828	SingularDTV	SNGLS	SingularDTV
829	Dentacoin	DCN	Dentacoin
830	Cryptocean	CRON	Cryptocean
831	AS Roma Fan Token	ASR	AS Roma Fan Token
832	Hyperion	HYN	Hyperion
833	Orient Walt	HTDF	Orient Walt
834	Swapcoinz	SPAZ	Swapcoinz
835	Mirrored Tesla	mTSLA	Mirrored Tesla
836	DEAPcoin	DEP	DEAPcoin
837	Mirrored Amazon	mAMZN	Mirrored Amazon
838	Odyssey	OCN	Odyssey
839	Mirrored Netflix	mNFLX	Mirrored Netflix
840	Pluton	PLU	Pluton
841	Lightning Bitcoin	LBTC	Lightning Bitcoin
842	Zano	ZANO	Zano
843	HOLD	HOLD	HOLD
844	Safe Haven	SHA	Safe Haven
845	Kebab Token	KEBAB	Kebab Token
846	PolySwarm	NCT	PolySwarm
847	Mirrored iShares Gold Trust	mIAU	Mirrored iShares Gold Trust
848	Cardstack	CARD	Cardstack
849	DAOstack	GEN	DAOstack
850	OG Fan Token	OG	OG Fan Token
851	United Traders Token	UTT	United Traders Token
852	Bitrue Coin	BTR	Bitrue Coin
853	Mobius	MOBI	Mobius
854	Mirrored Invesco QQQ Trust	mQQQ	Mirrored Invesco QQQ Trust
855	Pillar	PLR	Pillar
856	Covesting	COV	Covesting
857	UCA Coin	UCA	UCA Coin
858	SmartMesh	SMT	SmartMesh
859	TriumphX	TRIX	TriumphX
860	SunContract	SNC	SunContract
861	Monero Classic	XMC	Monero Classic
862	Mirrored Twitter	mTWTR	Mirrored Twitter
863	Aragon Court	ANJ	Aragon Court
864	DEJAVE	DJV	DEJAVE
865	All Sports	SOC	All Sports
866	Counterparty	XCP	Counterparty
867	Mirrored iShares Silver Trust	mSLV	Mirrored iShares Silver Trust
868	SONM	SNM	SONM
869	FREE Coin	FREE	FREE Coin
870	PlayFuel	PLF	PlayFuel
871	ELYSIA	EL	ELYSIA
872	Mirrored Alibaba	mBABA	Mirrored Alibaba
873	Diamond Platform Token	DPT	Diamond Platform Token
874	Goldcoin	GLC	Goldcoin
875	Mirrored Microsoft	mMSFT	Mirrored Microsoft
876	AIDUS TOKEN	AIDUS	AIDUS TOKEN
877	Origin Dollar	OUSD	Origin Dollar
878	T.OS	TOSC	T.OS
879	Credits	CS	Credits
880	BitCore	BTX	BitCore
881	Crypto Village Accelerator	CVA	Crypto Village Accelerator
882	Darwinia Commitment Token	KTON	Darwinia Commitment Token
883	Mirrored United States Oil Fund	mUSO	Mirrored United States Oil Fund
884	Breezecoin	BRZE	Breezecoin
885	BitMart Token	BMX	BitMart Token
886	RigoBlock	GRG	RigoBlock
887	Emercoin	EMC	Emercoin
888	Minter Network	BIP	Minter Network
889	ColossusXT	COLX	ColossusXT
890	Valor Token	VALOR	Valor Token
891	TEMCO	TEMCO	TEMCO
892	SpankChain	SPANK	SpankChain
893	Raven Protocol	RAVEN	Raven Protocol
894	MAP Protocol	MAP	MAP Protocol
895	Penta	PNT	Penta
896	DragonVein	DVC	DragonVein
897	Neumark	NEU	Neumark
898	Diamond	DMD	Diamond
899	Mirrored ProShares VIX	mVIXY	Mirrored ProShares VIX
900	Venus DAI	vDAI	Venus DAI
901	Remme	REM	Remme
902	ScPrime	SCP	ScPrime
903	Game.com	GTC	Game.com
904	QuadrantProtocol	EQUAD	QuadrantProtocol
905	Precium	PCM	Precium
906	FLETA	FLETA	FLETA
907	WeShow Token	WET	WeShow Token
908	Ternio	TERN	Ternio
909	Blockchain Certified Data Token	BCDT	Blockchain Certified Data Token
910	Nexty	NTY	Nexty
911	EOS Force	EOSC	EOS Force
912	BackPacker Coin	BPC	BackPacker Coin
913	apM Coin	APM	apM Coin
914	Hyprr (Howdoo)	UDOO	Hyprr (Howdoo)
915	Flash	FLASH	Flash
916	Hydro Protocol	HOT	Hydro Protocol
917	Chi Gastoken	CHI	Chi Gastoken
918	DeepBrain Chain	DBC	DeepBrain Chain
919	Pundi X NEM	NPXSXEM	Pundi X NEM
920	PolkaBridge	PBR	PolkaBridge
921	Seigniorage Shares	SHARE	Seigniorage Shares
922	Callisto Network	CLO	Callisto Network
923	fyeth.finance	YETH	fyeth.finance
924	PANTHEON X	XPN	PANTHEON X
925	Genaro Network	GNX	Genaro Network
926	Earneo	RNO	Earneo
927	NuBits	USNBT	NuBits
928	UnlimitedIP	UIP	UnlimitedIP
929	Safe	SAFE	Safe
930	Peculium	PCL	Peculium
931	BIDR	BIDR	BIDR
932	MediShares	MDS	MediShares
933	Phore	PHR	Phore
934	QuickX Protocol	QCX	QuickX Protocol
935	Verasity	VRA	Verasity
936	ProximaX	XPX	ProximaX
937	Fuse Network	FUSE	Fuse Network
938	Nord Finance	NORD	Nord Finance
939	ZIMBOCASH	ZASH	ZIMBOCASH
940	Origo	OGO	Origo
941	NewYorkCoin	NYC	NewYorkCoin
942	ODEM	ODE	ODEM
943	Amoveo	VEO	Amoveo
944	Level01	LVX	Level01
945	Atlas Protocol	ATP	Atlas Protocol
946	PressOne	PRS	PressOne
947	Skrumble Network	SKM	Skrumble Network
948	CoTrader	COT	CoTrader
949	Parachute	PAR	Parachute
950	Abyss	ABYSS	Abyss
951	BUX Token	BUX	BUX Token
952	Litecoin Cash	LCC	Litecoin Cash
953	Lykke	LKK	Lykke
954	Fatcoin	FAT	Fatcoin
955	MIR COIN	MIR	MIR COIN
956	VeriDocGlobal	VDG	VeriDocGlobal
957	Rapidz	RPZX	Rapidz
958	Digix Gold Token	DGX	Digix Gold Token
959	Zel	ZEL	Zel
960	Dune Network	DUN	Dune Network
961	GAPS	GAP	GAPS
962	Egretia	EGT	Egretia
963	Tripio	TRIO	Tripio
964	ALQO	XLQ	ALQO
965	qiibee	QBX	qiibee
966	Zenfuse	ZEFU	Zenfuse
967	bitCNY	BITCNY	bitCNY
968	dForce USDx	USDX	dForce USDx
969	MASQ	MASQ	MASQ
970	OKCash	OK	OKCash
971	BitGreen	BITG	BitGreen
972	MyWish	WISH	MyWish
973	Nsure.Network	NSURE	Nsure.Network
974	VNX Exchange	VNXLU	VNX Exchange
975	Traceability Chain	TAC	Traceability Chain
976	Banano	BAN	Banano
977	NoLimitCoin	NLC2	NoLimitCoin
978	CasinoCoin	CSC	CasinoCoin
979	CryptoPing	PING	CryptoPing
980	Dev Protocol	DEV	Dev Protocol
981	FinNexus	FNX	FinNexus
982	DMM: Governance	DMG	DMM: Governance
983	Growth DeFi	GRO	Growth DeFi
984	Blocery	BLY	Blocery
985	Minereum	MNE	Minereum
986	Smartlands Network	SLT	Smartlands Network
987	Spaceswap	MILK2	Spaceswap
988	DECOIN	DTEP	DECOIN
989	ToaCoin	TOA	ToaCoin
990	HitChain	HIT	HitChain
991	BOLT	BOLT	BOLT
992	Aeon	AEON	Aeon
993	e-Gulden	EFL	e-Gulden
994	YOU COIN	YOU	YOU COIN
995	Offshift	XFT	Offshift
996	OTOCASH	OTO	OTOCASH
997	ZClassic	ZCL	ZClassic
998	DePay	DEPAY	DePay
999	XYO	XYO	XYO
1000	UGAS	UGAS	UGAS
\.


--
-- Data for Name: flyway_schema_history; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.flyway_schema_history (installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success) FROM stdin;
1	1	init	SQL	V1__init.sql	25619101	postgres	2021-03-08 20:08:30.516581	116	t
2	2	tickers	SQL	V2__tickers.sql	839167064	postgres	2021-03-08 20:08:30.865666	852	t
3	9999999999	test	SQL	V9999999999__test.sql	1891279725	postgres	2021-03-08 20:08:31.901396	250	t
\.


--
-- Data for Name: likes; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.likes (id, user_id, post_type, post_id, date) FROM stdin;
1	5	0	1	2021-02-10 10:34:58.282
2	5	0	10	2021-02-10 10:35:13.195
3	5	0	19	2021-02-10 10:35:16.404
4	5	0	20	2021-02-10 10:35:25.673
5	5	0	17	2021-02-10 10:35:49.144
6	5	0	18	2021-02-10 10:35:51.419
7	4	0	1	2021-02-10 10:36:15.638
8	4	0	2	2021-02-10 10:36:17.316
9	4	0	3	2021-02-10 10:36:18.862
10	4	0	4	2021-02-10 10:36:20.263
11	4	0	5	2021-02-10 10:36:21.779
12	4	0	6	2021-02-10 10:36:23.555
13	4	0	7	2021-02-10 10:36:25.246
14	4	0	9	2021-02-10 10:36:27.122
15	4	0	19	2021-02-10 10:36:35.36
16	3	0	1	2021-02-10 10:37:07.102
17	3	0	2	2021-02-10 10:37:08.819
18	3	0	4	2021-02-10 10:37:10.49
19	3	0	6	2021-02-10 10:37:12.243
20	3	0	10	2021-02-10 10:37:14.538
21	3	0	14	2021-02-10 10:37:17.027
22	3	0	19	2021-02-10 10:37:19.914
23	3	0	5	2021-02-10 13:08:44.303
24	3	2	1	2021-02-10 13:09:25.977
25	3	2	2	2021-02-10 13:09:35.833
26	3	2	3	2021-02-10 13:09:37.475
27	3	1	1	2021-02-10 14:08:48.663
28	3	1	3	2021-02-10 14:08:50.52
29	3	1	7	2021-02-10 14:08:52.148
30	3	1	10	2021-02-10 14:08:53.683
31	3	1	12	2021-02-10 14:08:55
32	3	1	14	2021-02-10 14:09:00.089
33	3	1	19	2021-02-10 14:09:02.913
34	5	1	19	2021-02-10 14:11:19.056
35	5	1	27	2021-02-10 14:11:23.207
36	5	1	1	2021-02-10 14:11:24.321
37	5	1	2	2021-02-10 14:11:29.664
38	5	1	5	2021-02-10 14:11:32.426
39	5	1	7	2021-02-10 14:11:36.436
40	5	1	29	2021-02-10 14:11:39.955
41	5	1	31	2021-02-10 14:11:42.062
42	5	0	5	2021-02-10 14:13:35.264
44	2	2	227	2021-03-08 20:11:05.128
45	2	2	226	2021-03-08 20:11:06.8
48	2	2	225	2021-03-08 20:11:13.27
49	2	2	224	2021-03-08 20:53:33.455
50	2	2	228	2021-03-08 20:53:42.403
55	2	2	258	2021-03-09 23:26:29.898
\.


--
-- Data for Name: news; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.news (id, created_by, title, description, image_url, content, tags, source, created, updated) FROM stdin;
1	2	Bitcoin is a new gold	short description1	\N	Bitcoin is an old crypto	{bitcoin,ethereum}	bellingcat	2021-02-10 10:43:24.942	\N
2	2	Who cares about this title	short description2	\N	How #ETH Ethereum changed the world	{tag1,tag2}	navalny	2021-02-10 10:43:51.073	\N
3	2	The best title ever	short description3	\N	Why Bitcoin can overtake other cryptos	{ripple}	source	2021-02-10 10:44:07.001	\N
4	\N	Cardano (ADA) Beats BNB And Enters The Top 3 Of The World’s Largest Cryptocurrencies	Cardano-ADA-Beats-BNB-And-Enters-The-Top-3-Of-The-Worlds-Largest-Cryptocurrencies	\N	Cardano (ADA) Beats BNB And Enters The Top 3 Of The World’s Largest Cryptocurrencies	{cardano,"binance coin"}	Feed - Cryptopotato.Com	2021-02-27 03:48:22	\N
5	\N	Veteran Analyst Explains Why is Bearish on Bitcoin Right Now, Says $100K ‘Years Away’	Veteran-Analyst-Explains-Why-is-Bearish-on-Bitcoin-Right-Now-Says-100K-Years-Away	\N	Veteran Analyst Explains Why is Bearish on Bitcoin Right Now, Says $100K ‘Years Away’	{bitcoin}	CryptoGlobe	2021-02-27 02:59:00	\N
6	\N	Why Is Cardano Pumping While Other Cryptocurrencies Are Slumping?	Why-Is-Cardano-Pumping-While-Other-Cryptocurrencies-Are-Slumping	\N	Why Is Cardano Pumping While Other Cryptocurrencies Are Slumping?	{bitcoin,cardano}	Decrypt	2021-02-27 02:42:41	\N
7	\N	Why Cathie Wood Thinks Bitcoin Could Replace Bonds	Why-Cathie-Wood-Thinks-Bitcoin-Could-Replace-Bonds	\N	Why Cathie Wood Thinks Bitcoin Could Replace Bonds	{bitcoin}	The Breakdown	2021-02-27 02:00:00	\N
8	\N	Bid Side In Bitcoin Disappears Amidst Jittering “Macro Environment”	Bid-Side-In-Bitcoin-Disappears-Amidst-Jittering-Macro-Environment	\N	Bid Side In Bitcoin Disappears Amidst Jittering “Macro Environment”	{bitcoin}	NewsBTC	2021-02-27 01:00:51	\N
9	\N	Auf Bitcoin wetten, ohne BTC zu besitzen – wie geht das?	Auf-Bitcoin-wetten-ohne-BTC-zu-besitzen-wie-geht-das	\N	Auf Bitcoin wetten, ohne BTC zu besitzen – wie geht das?	{bitcoin}	BTC ECHO	2021-02-27 07:00:00	\N
10	\N	Crypto can be lucrative, but make sure you’re ready for the taxman	Crypto-can-be-lucrative-but-make-sure-youre-ready-for-the-taxman	\N	Crypto can be lucrative, but make sure you’re ready for the taxman	\N	CoinTelegraph	2021-02-27 06:17:00	\N
11	\N	Last chance to enter 	Last-chance-to-enter	\N	Last chance to enter 	{"binance coin"}	Binance Coin Twitter	2021-02-27 05:30:00	\N
12	\N	Grayscale's Bitcoin premium has dropped to record lows below zero	Grayscales-Bitcoin-premium-has-dropped-to-record-lows-below-zero	\N	Grayscale's Bitcoin premium has dropped to record lows below zero	{bitcoin}	CoinTelegraph	2021-02-27 11:19:45	\N
13	\N	3 Gründe, warum Ethereum immer noch besser ist als die BSC	3-Grunde-warum-Ethereum-immer-noch-besser-ist-als-die-BSC	\N	3 Gründe, warum Ethereum immer noch besser ist als die BSC	{ethereum}	BTC ECHO	2021-02-27 11:00:00	\N
14	\N	Cardano Reaches All-Time High, Ahead of Ethereum in Transaction Volume	Cardano-Reaches-All-Time-High-Ahead-of-Ethereum-in-Transaction-Volume	\N	Cardano Reaches All-Time High, Ahead of Ethereum in Transaction Volume	{ethereum,cardano}	Bitcoinist	2021-02-27 11:00:00	\N
15	\N	Ripple Partner SoftBank Supports Chinese ZT Crypto Exchange with Multi-Million USD Sum	Ripple-Partner-SoftBank-Supports-Chinese-ZT-Crypto-Exchange-with-Multi-Million-USD-Sum	\N	Ripple Partner SoftBank Supports Chinese ZT Crypto Exchange with Multi-Million USD Sum	\N	U.Today	2021-02-27 10:37:51	\N
16	\N	Kraken to Double its Valuation to $10 Billion via Funding Round	Kraken-to-Double-its-Valuation-to-10-Billion-via-Funding-Round	\N	Kraken to Double its Valuation to $10 Billion via Funding Round	{ethereum}	Feed - Cryptopotato.Com	2021-02-27 10:35:49	\N
17	\N	Ökonom offenbart bittere Aussichten, falls Bitcoin jemals zum Welterfolg werden sollte	Okonom-offenbart-bittere-Aussichten-falls-Bitcoin-jemals-zum-Welterfolg-werden-sollte	\N	Ökonom offenbart bittere Aussichten, falls Bitcoin jemals zum Welterfolg werden sollte	{bitcoin}	CoinUpdate.de	2021-02-27 10:35:00	\N
18	\N	Why Is Cardano ADA Pumping? - Can Cardano Hit $32?	Why-Is-Cardano-ADA-Pumping-Can-Cardano-Hit-32	\N	Why Is Cardano ADA Pumping? - Can Cardano Hit $32?	{cardano}	The Modern Investor	2021-02-27 10:30:03	\N
19	\N	Taraxa’s Steven Pu on the Elephant-in-the-Room Problem With Informal Transactions,  and How He Drives the dApps Adoption With Slack-Inspired Go-to-Market	Taraxas-Steven-Pu-on-the-Elephant-in-the-Room-Problem-With-Informal-Transactions-and-How-He-Drives-the-dApps-Adoption-With-Slack-Inspired-Go-to-Market	\N	Taraxa’s Steven Pu on the Elephant-in-the-Room Problem With Informal Transactions,  and How He Drives the dApps Adoption With Slack-Inspired Go-to-Market	\N	NewsBTC	2021-02-27 10:00:04	\N
20	\N	Großer Dubai-Krypto-Fonds stößt Bitcoin für $750M ab – um Cardano und Polkadot zu kaufen	Groer-Dubai-Krypto-Fonds-stot-Bitcoin-fur-750M-ab-um-Cardano-und-Polkadot-zu-kaufen	\N	Großer Dubai-Krypto-Fonds stößt Bitcoin für $750M ab – um Cardano und Polkadot zu kaufen	{bitcoin,ethereum,cardano,polkadot}	CoinUpdate.de	2021-02-27 09:35:00	\N
21	\N	Coinbase hält Bitcoin-Schöpfer Satoshi Nakamoto für eine Bedrohung – doch weshalb?	Coinbase-halt-Bitcoin-Schopfer-Satoshi-Nakamoto-fur-eine-Bedrohung-doch-weshalb	\N	Coinbase hält Bitcoin-Schöpfer Satoshi Nakamoto für eine Bedrohung – doch weshalb?	{bitcoin}	CoinUpdate.de	2021-02-27 09:21:33	\N
22	\N	Institutions Withdraw Massive Amount of Bitcoin from Coinbase, Moving It to Cold Storage	Institutions-Withdraw-Massive-Amount-of-Bitcoin-from-Coinbase-Moving-It-to-Cold-Storage	\N	Institutions Withdraw Massive Amount of Bitcoin from Coinbase, Moving It to Cold Storage	{bitcoin}	U.Today	2021-02-27 09:04:01	\N
23	\N	Singapore Develops Blockchain-based Verification Standard for Covid-19 Test	Singapore-Develops-Blockchain-based-Verification-Standard-for-Covid-19-Test	\N	Singapore Develops Blockchain-based Verification Standard for Covid-19 Test	\N	BTC Manager	2021-02-27 09:00:04	\N
24	\N	Bitcoin-Börse Kraken: Unternehmensbewertung bei 10 Milliarden US-Dollar	Bitcoin-Borse-Kraken-Unternehmensbewertung-bei-10-Milliarden-US-Dollar	\N	Bitcoin-Börse Kraken: Unternehmensbewertung bei 10 Milliarden US-Dollar	\N	BTC ECHO	2021-02-27 09:00:00	\N
25	\N	CoinShares Launches a $75 Million Physically-Backed Ethereum (ETH) ETP	CoinShares-Launches-a-75-Million-Physically-Backed-Ethereum-ETH-ETP	\N	CoinShares Launches a $75 Million Physically-Backed Ethereum (ETH) ETP	{bitcoin,ethereum}	Feed - Cryptopotato.Com	2021-02-27 15:10:12	\N
26	\N	SEC Explains Itself by Issuing a Risk Alert for Crypto Assets	SEC-Explains-Itself-by-Issuing-a-Risk-Alert-for-Crypto-Assets	\N	SEC Explains Itself by Issuing a Risk Alert for Crypto Assets	\N	BeInCrypto	2021-02-27 15:01:05	\N
183	\N	Is South Korea Returning?	Is-South-Korea-Returning	\N	Is South Korea Returning?	{bitcoin}	Trustnodes	2021-03-01 15:12:13	\N
27	\N	"Bitcoin ist das erste globale Geldsystem": CEO-Treffen in Bullenstimmung	Bitcoin-ist-das-erste-globale-Geldsystem-CEO-Treffen-in-Bullenstimmung	\N	"Bitcoin ist das erste globale Geldsystem": CEO-Treffen in Bullenstimmung	{bitcoin}	BTC ECHO	2021-02-27 15:00:00	\N
28	\N	Moving beyond the crisis narrative: Crypto in a post-pandemic world	Moving-beyond-the-crisis-narrative-Crypto-in-a-post-pandemic-world	\N	Moving beyond the crisis narrative: Crypto in a post-pandemic world	\N	CoinTelegraph	2021-02-27 14:52:00	\N
29	\N	BTC, ETH, XRP, BNB, ADA and XLM Price Analysis for February 27	BTC-ETH-XRP-BNB-ADA-and-XLM-Price-Analysis-for-February-27	\N	BTC, ETH, XRP, BNB, ADA and XLM Price Analysis for February 27	{bitcoin,cardano,stellar}	U.Today	2021-02-27 14:41:35	\N
30	\N	Grayscale will führende DeFi-Projekte an der Börse handelbar machen	Grayscale-will-fuhrende-DeFi-Projekte-an-der-Borse-handelbar-machen	\N	Grayscale will führende DeFi-Projekte an der Börse handelbar machen	{bitcoin,cardano,polkadot,"bitcoin cash",uniswap,cosmos,"synthetix network token",compound,compound,yearn.finance}	CoinTelegraph DE	2021-02-27 14:29:34	\N
31	\N	25,600 Bitcoin (BTC) Worth $1.2B Has Left Coinbase in the Past Week	25600-Bitcoin-BTC-Worth-12B-Has-Left-Coinbase-in-the-Past-Week	\N	25,600 Bitcoin (BTC) Worth $1.2B Has Left Coinbase in the Past Week	{bitcoin}	Ethereum World News	2021-02-27 14:23:32	\N
32	\N	A Single Whale Just Moved $333 Million Bitcoin	A-Single-Whale-Just-Moved-333-Million-Bitcoin	\N	A Single Whale Just Moved $333 Million Bitcoin	{bitcoin}	Decrypt	2021-02-27 14:14:53	\N
33	\N	Test Your Crypto Knowledge With BeInCrypto’s Weekly Quiz — Feb. 27	Test-Your-Crypto-Knowledge-With-BeInCryptos-Weekly-Quiz-Feb-27	\N	Test Your Crypto Knowledge With BeInCrypto’s Weekly Quiz — Feb. 27	{bitcoin}	BeInCrypto	2021-02-27 13:58:00	\N
34	\N	While Washington dithers, Wyoming and other US states mine for crypto gold	While-Washington-dithers-Wyoming-and-other-US-states-mine-for-crypto-gold	\N	While Washington dithers, Wyoming and other US states mine for crypto gold	\N	CoinTelegraph	2021-02-27 13:47:00	\N
35	\N	Cardano Comes Out Kicking While Bitcoin and Ethereum Slump	Cardano-Comes-Out-Kicking-While-Bitcoin-and-Ethereum-Slump	\N	Cardano Comes Out Kicking While Bitcoin and Ethereum Slump	{bitcoin,ethereum,cardano}	Decrypt	2021-02-27 13:38:42	\N
36	\N	India: Are authorities really seizing crypto hardware wallets?	India-Are-authorities-really-seizing-crypto-hardware-wallets	\N	India: Are authorities really seizing crypto hardware wallets?	\N	AMBCrypto	2021-02-27 13:35:46	\N
37	\N	The US Government Just Unwittingly Made The Biggest PR Move For Bitcoin	The-US-Government-Just-Unwittingly-Made-The-Biggest-PR-Move-For-Bitcoin	\N	The US Government Just Unwittingly Made The Biggest PR Move For Bitcoin	{bitcoin}	ZyCrypto	2021-02-27 13:28:41	\N
38	\N	Report: Bitcoin Whales Sold At Least 140,000 BTC in February as Price Suffers	Report-Bitcoin-Whales-Sold-At-Least-140000-BTC-in-February-as-Price-Suffers	\N	Report: Bitcoin Whales Sold At Least 140,000 BTC in February as Price Suffers	{bitcoin}	Feed - Cryptopotato.Com	2021-02-27 13:20:07	\N
39	\N	Cardano’s ADA Rises 27.25%, Trading at $1.40	Cardanos-ADA-Rises-2725-Trading-at-140	\N	Cardano’s ADA Rises 27.25%, Trading at $1.40	{cardano}	U.Today	2021-02-27 13:15:47	\N
40	\N	Galaxy Digital benennt zwei große Hindernisse für Bitcoin-Investitionen durch Firmen	Galaxy-Digital-benennt-zwei-groe-Hindernisse-fur-Bitcoin-Investitionen-durch-Firmen	\N	Galaxy Digital benennt zwei große Hindernisse für Bitcoin-Investitionen durch Firmen	{bitcoin}	CoinTelegraph DE	2021-02-27 13:10:54	\N
41	\N	Where's the Trust in the $150 Million NFT Art Market?	Wheres-the-Trust-in-the-150-Million-NFT-Art-Market	\N	Where's the Trust in the $150 Million NFT Art Market?	\N	Decrypt	2021-02-27 13:08:03	\N
42	\N	Grayscale Exploring “New Investment Products” Based on DOT, LINK, ADA, AAVE, Others	Grayscale-Exploring-New-Investment-Products-Based-on-DOT-LINK-ADA-AAVE-Others	\N	Grayscale Exploring “New Investment Products” Based on DOT, LINK, ADA, AAVE, Others	{polkadot,chainlink,uniswap,"aave [OLD]"}	BTC Manager	2021-02-27 13:00:19	\N
43	\N	Cardano Price Analysis: ADA Sets New ATH, What Are The Next Targets?	Cardano-Price-Analysis-ADA-Sets-New-ATH-What-Are-The-Next-Targets	\N	Cardano Price Analysis: ADA Sets New ATH, What Are The Next Targets?	{cardano}	Feed - Cryptopotato.Com	2021-02-27 19:46:31	\N
44	\N	DeFi building block service Furucombo exploited for $14M	DeFi-building-block-service-Furucombo-exploited-for-14M	\N	DeFi building block service Furucombo exploited for $14M	{ethereum,aave}	The Block	2021-02-27 19:44:02	\N
45	\N	The current Bitcoin bull run is different; here’s why!	The-current-Bitcoin-bull-run-is-different-heres-why	\N	The current Bitcoin bull run is different; here’s why!	{bitcoin}	AMBCrypto	2021-02-27 19:30:14	\N
46	\N	BTC Miners Are Now Accumulating, not Selling	BTC-Miners-Are-Now-Accumulating-not-Selling	\N	BTC Miners Are Now Accumulating, not Selling	{bitcoin}	BeInCrypto	2021-02-27 19:10:18	\N
47	\N	Economist warns of dystopia if ‘Bitcoin Aristocrats’ become reality	Economist-warns-of-dystopia-if-Bitcoin-Aristocrats-become-reality	\N	Economist warns of dystopia if ‘Bitcoin Aristocrats’ become reality	{bitcoin}	CoinTelegraph	2021-02-27 19:08:23	\N
48	\N	„Krypto-Boom“ – Robinhood verzeichnet deutlich mehr Krypto-Nutzer als im Vorjahr	Krypto-Boom-Robinhood-verzeichnet-deutlich-mehr-Krypto-Nutzer-als-im-Vorjahr	\N	„Krypto-Boom“ – Robinhood verzeichnet deutlich mehr Krypto-Nutzer als im Vorjahr	{dogecoin}	CoinTelegraph DE	2021-02-27 18:59:45	\N
49	\N	6 Questions for Kain Warwick of Synthetix	6-Questions-for-Kain-Warwick-of-Synthetix	\N	6 Questions for Kain Warwick of Synthetix	\N	CoinTelegraph	2021-02-27 18:56:22	\N
50	\N	New Group of Crypto Assets Are Ready To Erupt, Says Trader Tyler Swope	New-Group-of-Crypto-Assets-Are-Ready-To-Erupt-Says-Trader-Tyler-Swope	\N	New Group of Crypto Assets Are Ready To Erupt, Says Trader Tyler Swope	\N	The Daily Hodl	2021-02-27 18:30:29	\N
51	\N	Here are 6 DEX tokens that have seen exponential growth in 2021	Here-are-6-DEX-tokens-that-have-seen-exponential-growth-in-2021	\N	Here are 6 DEX tokens that have seen exponential growth in 2021	{uniswap,sushi}	CoinTelegraph	2021-02-27 18:19:19	\N
52	\N	Mark Cuban Offers Support for Fantom (FTM) in Podcast  With CZ	Mark-Cuban-Offers-Support-for-Fantom-FTM-in-Podcast-With-CZ	\N	Mark Cuban Offers Support for Fantom (FTM) in Podcast  With CZ	{fantom}	BeInCrypto	2021-02-27 18:15:30	\N
53	\N	Finanzmärkte ziehen Kryptomarkt nach unten, Bitcoin geht seitwärts	Finanzmarkte-ziehen-Kryptomarkt-nach-unten-Bitcoin-geht-seitwarts	\N	Finanzmärkte ziehen Kryptomarkt nach unten, Bitcoin geht seitwärts	{bitcoin,cardano,"binance coin","basic attention token"}	CoinTelegraph DE	2021-02-27 17:42:10	\N
54	\N	Celsius is 5xing the dev team so they can work on 50 projects simultaneously	Celsius-is-5xing-the-dev-team-so-they-can-work-on-50-projects-simultaneously	\N	Celsius is 5xing the dev team so they can work on 50 projects simultaneously	{"celsius Network"}	Celsius Network Reddit	2021-02-27 17:30:11.371966	\N
55	\N	Nigeria VP Tells Central Bank to Regulate, not Forbid, Crypto	Nigeria-VP-Tells-Central-Bank-to-Regulate-not-Forbid-Crypto	\N	Nigeria VP Tells Central Bank to Regulate, not Forbid, Crypto	\N	BeInCrypto	2021-02-27 17:28:24	\N
56	\N	Top Trader Says Bitcoin Bottom Is In, Plots New BTC All-Time High in March	Top-Trader-Says-Bitcoin-Bottom-Is-In-Plots-New-BTC-All-Time-High-in-March	\N	Top Trader Says Bitcoin Bottom Is In, Plots New BTC All-Time High in March	{bitcoin}	The Daily Hodl	2021-02-27 17:10:52	\N
57	\N	3 reasons why Reef Finance, Bridge Mutual and Morpheus Network are rallying	3-reasons-why-Reef-Finance-Bridge-Mutual-and-Morpheus-Network-are-rallying	\N	3 reasons why Reef Finance, Bridge Mutual and Morpheus Network are rallying	\N	CoinTelegraph	2021-02-27 23:05:00	\N
58	\N	Are Beeple's $6.6M Sale and Christie's Auction a Turning Point for NFTs?	Are-Beeples-66M-Sale-and-Christies-Auction-a-Turning-Point-for-NFTs	\N	Are Beeple's $6.6M Sale and Christie's Auction a Turning Point for NFTs?	{bitcoin,tether}	The Breakdown	2021-02-27 23:00:00	\N
59	\N	EFF Director Cindy Cohn On Warrantless Surveillance, Encryption And Financial Privacy With Bitcoin	EFF-Director-Cindy-Cohn-On-Warrantless-Surveillance-Encryption-And-Financial-Privacy-With-Bitcoin	\N	EFF Director Cindy Cohn On Warrantless Surveillance, Encryption And Financial Privacy With Bitcoin	{bitcoin}	Forbes	2021-02-27 22:47:22	\N
60	\N	Top 6 Altcoins For March 2021, Best Altcoins To Buy, Crypto News 2021	TOP-6-ALTCOINS-FOR-MARCH-2021-BEST-ALTCOINS-TO-BUY-CRYPTO-NEWS-2021	\N	Top 6 Altcoins For March 2021, Best Altcoins To Buy, Crypto News 2021	{"bitpanda ecosystem Token"}	The Crypto Lark	2021-02-27 22:40:58	\N
61	\N	Billionaire Mark Cuban Says Crypto Is About To Change How All Businesses Work – Here’s How	Billionaire-Mark-Cuban-Says-Crypto-Is-About-To-Change-How-All-Businesses-Work-Heres-How	\N	Billionaire Mark Cuban Says Crypto Is About To Change How All Businesses Work – Here’s How	\N	The Daily Hodl	2021-02-27 22:15:58	\N
62	\N	DeFi Tx Bundler Furucombo Hacked for $14 Million	DeFi-Tx-Bundler-Furucombo-Hacked-for-14-Million	\N	DeFi Tx Bundler Furucombo Hacked for $14 Million	{ethereum}	CryptoBriefing	2021-02-27 22:07:37	\N
63	\N	Bill Gates on Bitcoin: If you have less money than Elon, watch out	Bill-Gates-on-Bitcoin-If-you-have-less-money-than-Elon-watch-out	\N	Bill Gates on Bitcoin: If you have less money than Elon, watch out	{bitcoin}	Finbold	2021-02-27 21:35:14	\N
64	\N	Wider den eigenen Thesen? – JPMorgan rät nun doch zu Bitcoin als Absicherungsmittel	Wider-den-eigenen-Thesen-JPMorgan-rat-nun-doch-zu-Bitcoin-als-Absicherungsmittel	\N	Wider den eigenen Thesen? – JPMorgan rät nun doch zu Bitcoin als Absicherungsmittel	{bitcoin}	CoinTelegraph DE	2021-02-27 21:17:00	\N
65	\N	Bitcoin plunges, Ethereum suffers, Musk loses billions: Hodler’s Digest, Feb. 21–27	Bitcoin-plunges-Ethereum-suffers-Musk-loses-billions-Hodlers-Digest-Feb-2127	\N	Bitcoin plunges, Ethereum suffers, Musk loses billions: Hodler’s Digest, Feb. 21–27	{bitcoin,ethereum}	CoinTelegraph	2021-02-27 21:09:34	\N
66	\N	Billion-Dollar Whale Transfer May Have Triggered Crypto Crash: Report	Billion-Dollar-Whale-Transfer-May-Have-Triggered-Crypto-Crash-Report	\N	Billion-Dollar Whale Transfer May Have Triggered Crypto Crash: Report	{bitcoin}	CryptoGlobe	2021-02-28 03:29:00	\N
67	\N	Binance CEO: Ethereum is For The Rich Guys, But Soon They’ll Be Poor	Binance-CEO-Ethereum-is-For-The-Rich-Guys-But-Soon-Theyll-Be-Poor	\N	Binance CEO: Ethereum is For The Rich Guys, But Soon They’ll Be Poor	{ethereum}	Feed - Cryptopotato.Com	2021-02-28 02:37:26	\N
68	\N	Square CFO: 1 Million Cash App Users Purchased BTC for the First Time	Square-CFO-1-Million-Cash-App-Users-Purchased-BTC-for-the-First-Time	\N	Square CFO: 1 Million Cash App Users Purchased BTC for the First Time	{bitcoin}	CryptoGlobe	2021-02-28 01:29:00	\N
69	\N	Professional traders need a global crypto sea, not hundreds of lakes	Professional-traders-need-a-global-crypto-sea-not-hundreds-of-lakes	\N	Professional traders need a global crypto sea, not hundreds of lakes	\N	CoinTelegraph	2021-02-28 07:32:00	\N
70	\N	Alles im Blick: Die spannendsten Krypto-News der Woche	Alles-im-Blick-Die-spannendsten-Krypto-News-der-Woche	\N	Alles im Blick: Die spannendsten Krypto-News der Woche	{cardano}	BTC ECHO	2021-02-28 07:00:00	\N
71	\N	Bitcoin Kurs in der Waagschale, BTC als Sparmethode für 1.000.000.000 Menschen, die Bewegungen der Großinvestoren, so hoch soll ETH noch steigen & Ripple News	Bitcoin-Kurs-in-der-Waagschale-BTC-als-Sparmethode-fur-1000000000-Menschen-die-Bewegungen-der-Groinvestoren-so-hoch-soll-ETH-noch-steigen-Ripple-News	\N	Bitcoin Kurs in der Waagschale, BTC als Sparmethode für 1.000.000.000 Menschen, die Bewegungen der Großinvestoren, so hoch soll ETH noch steigen & Ripple News	{bitcoin,ethereum,xrp}	CryptoMonday	2021-02-28 06:00:21	\N
72	\N	Trading on #Binance with a view	Trading-on-Binance-with-a-view	\N	Trading on #Binance with a view	{"binance coin"}	Binance Coin Twitter	2021-02-28 05:30:05	\N
73	\N	Researcher Believes He May Have Discovered Satoshi Nakamoto’s Twitter Account	Researcher-Believes-He-May-Have-Discovered-Satoshi-Nakamotos-Twitter-Account	\N	Researcher Believes He May Have Discovered Satoshi Nakamoto’s Twitter Account	{bitcoin}	CryptoGlobe	2021-02-28 05:29:00	\N
74	\N	RT @DenisCpoker: #TRON is ready to prove these Haters wrong.  Its time to go from undervalued back to the Top 10!  #trx	RT-DenisCpoker-TRON-is-ready-to-prove-these-Haters-wrong-Its-time-to-go-from-undervalued-back-to-the-Top-10-trx	\N	RT @DenisCpoker: #TRON is ready to prove these Haters wrong. Its time to go from undervalued back to the Top 10! #trx	{tron}	TRON Twitter	2021-02-28 05:21:51	\N
75	\N	Korean Government To Levy Taxes On Bitcoin Capital Gains Starting 2022	Korean-Government-To-Levy-Taxes-On-Bitcoin-Capital-Gains-Starting-2022	\N	Korean Government To Levy Taxes On Bitcoin Capital Gains Starting 2022	{bitcoin}	Feed - Cryptopotato.Com	2021-02-28 11:15:22	\N
76	\N	Ethereum Maximalism Turns Ugly with Insensitive Attack on Binance Smart Chain	Ethereum-Maximalism-Turns-Ugly-with-Insensitive-Attack-on-Binance-Smart-Chain	\N	Ethereum Maximalism Turns Ugly with Insensitive Attack on Binance Smart Chain	{ethereum}	U.Today	2021-02-28 11:15:00	\N
77	\N	Would You Buy Bitcoin For $6,000?	Would-You-Buy-Bitcoin-For-6000	\N	Would You Buy Bitcoin For $6,000?	{bitcoin}	The Modern Investor	2021-02-28 10:47:14	\N
78	\N	Bullish für BTC: Bitcoin-Miner akkumulieren jetzt – anstatt zu verkaufen	Bullish-fur-BTC-Bitcoin-Miner-akkumulieren-jetzt-anstatt-zu-verkaufen	\N	Bullish für BTC: Bitcoin-Miner akkumulieren jetzt – anstatt zu verkaufen	{bitcoin}	CoinUpdate.de	2021-02-28 10:45:00	\N
79	\N	Bitcoin sell-off over? Strong 'buy the dip' signal flashes for the first time in 5 months	Bitcoin-sell-off-over-Strong-buy-the-dip-signal-flashes-for-the-first-time-in-5-months	\N	Bitcoin sell-off over? Strong 'buy the dip' signal flashes for the first time in 5 months	{bitcoin}	CoinTelegraph	2021-02-28 10:09:47	\N
80	\N	Bitcoin Tech #2 - Nodes (Part 1) with Shinobi	Bitcoin-Tech-2-Nodes-Part-1-with-Shinobi	\N	Bitcoin Tech #2 - Nodes (Part 1) with Shinobi	{bitcoin}	whatbitcoindid	2021-02-28 10:06:16	\N
81	\N	Is Coinbase Preparing To Launch Its Own Crypto Asset? New Document Hints at New Token	Is-Coinbase-Preparing-To-Launch-Its-Own-Crypto-Asset-New-Document-Hints-at-New-Token	\N	Is Coinbase Preparing To Launch Its Own Crypto Asset? New Document Hints at New Token	\N	The Daily Hodl	2021-02-28 10:05:52	\N
82	\N	Krypto-Adaption: US-Börsenaufsicht veröffentlicht neuen Bericht zu Krypto-Regulierung	Krypto-Adaption-US-Borsenaufsicht-veroffentlicht-neuen-Bericht-zu-Krypto-Regulierung	\N	Krypto-Adaption: US-Börsenaufsicht veröffentlicht neuen Bericht zu Krypto-Regulierung	{bitcoin}	CoinUpdate.de	2021-02-28 09:45:00	\N
83	\N	Cardano Dominates Exchange Trading as Charles Hoskinson Calls Out "Salty" Haters	Cardano-Dominates-Exchange-Trading-as-Charles-Hoskinson-Calls-Out-Salty-Haters	\N	Cardano Dominates Exchange Trading as Charles Hoskinson Calls Out "Salty" Haters	{bitcoin,cardano}	U.Today	2021-02-28 09:39:00	\N
84	\N	China: Privatbanken helfen bei der Einführung des boomenden digitalen Yuan	China-Privatbanken-helfen-bei-der-Einfuhrung-des-boomenden-digitalen-Yuan	\N	China: Privatbanken helfen bei der Einführung des boomenden digitalen Yuan	{bitcoin}	CoinUpdate.de	2021-02-28 09:00:35	\N
85	\N	Investment-Gigant Andreesen Horowitz investiert in ein besseres Ethereum	Investment-Gigant-Andreesen-Horowitz-investiert-in-ein-besseres-Ethereum	\N	Investment-Gigant Andreesen Horowitz investiert in ein besseres Ethereum	{ethereum}	BTC ECHO	2021-02-28 09:00:00	\N
86	\N	XRP Plunges to Multi-Week Low, Tapping Key Support Level	XRP-Plunges-to-Multi-Week-Low-Tapping-Key-Support-Level	\N	XRP Plunges to Multi-Week Low, Tapping Key Support Level	{xrp}	U.Today	2021-02-28 08:59:00	\N
87	\N	BNB, RVN, VGX, XVS, & NPXS –  February’s Biggest Gainers	BNB-RVN-VGX-XVS-NPXS-Februarys-Biggest-Gainers	\N	BNB, RVN, VGX, XVS, & NPXS –  February’s Biggest Gainers	{"binance Coin","voyager token",ravencoin,"pundi x"}	BeInCrypto	2021-02-28 08:34:08	\N
88	\N	Bitcoin Plunges Below $44K as Miners Capitulate: Market Watch	Bitcoin-Plunges-Below-44K-as-Miners-Capitulate-Market-Watch	\N	Bitcoin Plunges Below $44K as Miners Capitulate: Market Watch	{bitcoin,ethereum}	Feed - Cryptopotato.Com	2021-02-28 08:24:51	\N
89	\N	RT @DenisCpoker: #TRON is ready to prove these Haters wrong. Its time to go from undervalued back to the Top 10! #trx	RT-DenisCpoker-TRON-is-ready-to-prove-these-Haters-wrong-Its-time-to-go-from-undervalued-back-to-the-Top-10-trx	\N	RT @DenisCpoker: #TRON is ready to prove these Haters wrong. Its time to go from undervalued back to the Top 10! #trx	{tron}	TRON Twitter	2021-02-28 05:21:51	\N
90	\N	Vize-Präsident von Nigeria sieht in Kryptowährungen Konkurrenz für Banken	Vize-Prasident-von-Nigeria-sieht-in-Kryptowahrungen-Konkurrenz-fur-Banken	\N	Vize-Präsident von Nigeria sieht in Kryptowährungen Konkurrenz für Banken	{bitcoin}	CoinTelegraph DE	2021-02-28 15:43:01	\N
91	\N	On-chain stablecoin volume surpasses $360 billion for February	On-chain-stablecoin-volume-surpasses-360-billion-for-February	\N	On-chain stablecoin volume surpasses $360 billion for February	{tether,"usd coin",dai}	The Block	2021-02-28 15:40:31	\N
92	\N	FuruCombo (COMBO) and ArmorFi (ARMOR) DeFis Attacked Today, $15 Million Lost. Here's What Happened	FuruCombo-COMBO-and-ArmorFi-ARMOR-DeFis-Attacked-Today-15-Million-Lost-Heres-What-Happened	\N	FuruCombo (COMBO) and ArmorFi (ARMOR) DeFis Attacked Today, $15 Million Lost. Here's What Happened	\N	U.Today	2021-02-28 15:14:44	\N
93	\N	Furucombo DEX and Bundler Hacked for $15 Million	Furucombo-DEX-and-Bundler-Hacked-for-15-Million	\N	Furucombo DEX and Bundler Hacked for $15 Million	\N	BeInCrypto	2021-02-28 15:10:34	\N
94	\N	Bitcoin Suisse Gründer: „Ich gebe seit 10 Jahren dieselbe Kursprognose“	Bitcoin-Suisse-Grunder-Ich-gebe-seit-10-Jahren-dieselbe-Kursprognose	\N	Bitcoin Suisse Gründer: „Ich gebe seit 10 Jahren dieselbe Kursprognose“	{bitcoin}	BTC ECHO	2021-02-28 15:00:00	\N
95	\N	As Bitcoin (BTC) Drops to $43,300, Number of Whales Hits Two-Year Low	As-Bitcoin-BTC-Drops-to-43300-Number-of-Whales-Hits-Two-Year-Low	\N	As Bitcoin (BTC) Drops to $43,300, Number of Whales Hits Two-Year Low	{bitcoin}	U.Today	2021-02-28 14:56:46	\N
96	\N	wNews: Unpacking Crypto’s “Facebook Moment”	wNews-Unpacking-Cryptos-Facebook-Moment	\N	wNews: Unpacking Crypto’s “Facebook Moment”	{bitcoin,ethereum,tether,tron}	CryptoBriefing	2021-02-28 14:50:33	\N
97	\N	Geht der Rekordlauf von Bitcoin weiter? – „Torschlusspanik“ hat noch nicht eingesetzt	Geht-der-Rekordlauf-von-Bitcoin-weiter-Torschlusspanik-hat-noch-nicht-eingesetzt	\N	Geht der Rekordlauf von Bitcoin weiter? – „Torschlusspanik“ hat noch nicht eingesetzt	{bitcoin}	CoinTelegraph DE	2021-02-28 14:49:07	\N
98	\N	Is Bitcoin Likely to Hit $41,000 then $75,000+ - Let's Ask Willy Woo!!!	Is-Bitcoin-Likely-to-Hit-41000-then-75000-Lets-Ask-Willy-Woo	\N	Is Bitcoin Likely to Hit $41,000 then $75,000+ - Let's Ask Willy Woo!!!	{bitcoin}	Tone Vays	2021-02-28 14:45:04	\N
99	\N	Dubai-Based Crypto Fund Selling $750,000,000 in Bitcoin To Buy Two Altcoins	Dubai-Based-Crypto-Fund-Selling-750000000-in-Bitcoin-To-Buy-Two-Altcoins	\N	Dubai-Based Crypto Fund Selling $750,000,000 in Bitcoin To Buy Two Altcoins	{bitcoin}	The Daily Hodl	2021-02-28 14:35:45	\N
100	\N	Bitcoin Falls Below $44k As Crypto Prices Continue to Slide	Bitcoin-Falls-Below-44k-As-Crypto-Prices-Continue-to-Slide	\N	Bitcoin Falls Below $44k As Crypto Prices Continue to Slide	{bitcoin}	Decrypt	2021-02-28 14:24:18	\N
101	\N	Latest Ethereum DeFi exploit sees $14 million stolen from ‘Furucombo’	Latest-Ethereum-DeFi-exploit-sees-14-million-stolen-from-Furucombo	\N	Latest Ethereum DeFi exploit sees $14 million stolen from ‘Furucombo’	{ethereum}	CryptoSlate	2021-02-28 13:47:11	\N
102	\N	Crypto Exchange Accidentally Sells Bitcoin at 88% Discount, Asks for BTC Back	Crypto-Exchange-Accidentally-Sells-Bitcoin-at-88-Discount-Asks-for-BTC-Back	\N	Crypto Exchange Accidentally Sells Bitcoin at 88% Discount, Asks for BTC Back	{bitcoin}	CryptoGlobe	2021-02-28 13:29:00	\N
103	\N	Vice President of Nigeria Tips Cryptocurrencies To Challenge Traditional Banking	Vice-President-of-Nigeria-Tips-Cryptocurrencies-To-Challenge-Traditional-Banking	\N	Vice President of Nigeria Tips Cryptocurrencies To Challenge Traditional Banking	{bitcoin}	Feed - Cryptopotato.Com	2021-02-28 13:25:59	\N
104	\N	Transparent stablecoins? Conclusion of Tether vs. NYAG raises new questions	Transparent-stablecoins-Conclusion-of-Tether-vs-NYAG-raises-new-questions	\N	Transparent stablecoins? Conclusion of Tether vs. NYAG raises new questions	{Tether}	CoinTelegraph	2021-02-28 13:07:00	\N
105	\N	ETH Price Analysis: After Losing 30% This Week, Ethereum Facing Crucial Support At Previous ATH	ETH-Price-Analysis-After-Losing-30-This-Week-Ethereum-Facing-Crucial-Support-At-Previous-ATH	\N	ETH Price Analysis: After Losing 30% This Week, Ethereum Facing Crucial Support At Previous ATH	{ethereum}	Feed - Cryptopotato.Com	2021-02-28 13:04:42	\N
106	\N	Former deputy governor of The Central Bank of Iran warns on Bitcoin	Former-deputy-governor-of-The-Central-Bank-of-Iran-warns-on-Bitcoin	\N	Former deputy governor of The Central Bank of Iran warns on Bitcoin	{bitcoin}	Finbold	2021-02-28 13:01:59	\N
107	\N	Central Bank of Ghana Launches Regulatory Sandbox Pilot for Blockchain and Financial Projects	Central-Bank-of-Ghana-Launches-Regulatory-Sandbox-Pilot-for-Blockchain-and-Financial-Projects	\N	Central Bank of Ghana Launches Regulatory Sandbox Pilot for Blockchain and Financial Projects	\N	BTC Manager	2021-02-28 13:00:57	\N
108	\N	Does Ethereum’s bull run still have to wait for Bitcoin?	Does-Ethereums-bull-run-still-have-to-wait-for-Bitcoin	\N	Does Ethereum’s bull run still have to wait for Bitcoin?	{bitcoin,ethereum}	AMBCrypto	2021-02-28 13:00:29	\N
109	\N	Dogecoin hasn't always been a 'fun meme coin'	Dogecoin-hasnt-always-been-a-fun-meme-coin	\N	Dogecoin hasn't always been a 'fun meme coin'	{dogecoin}	CoinTelegraph	2021-02-28 13:00:00	\N
110	\N	Auf den Hund gekommen? – SEC „ermittelt“ wegen Dogecoin-Tweets von Musk	Auf-den-Hund-gekommen-SEC-ermittelt-wegen-Dogecoin-Tweets-von-Musk	\N	Auf den Hund gekommen? – SEC „ermittelt“ wegen Dogecoin-Tweets von Musk	{bitcoin,dogecoin}	CoinTelegraph DE	2021-02-28 19:39:14	\N
111	\N	Bitcoin Miners Net Position Turn Positive: A Crypto Bull Case?	Bitcoin-Miners-Net-Position-Turn-Positive-A-Crypto-Bull-Case	\N	Bitcoin Miners Net Position Turn Positive: A Crypto Bull Case?	{bitcoin}	Bitcoinist	2021-02-28 19:26:30	\N
112	\N	Weekend Roundup: Rows of Red as Bitcoin Taps $43,000	Weekend-Roundup-Rows-of-Red-as-Bitcoin-Taps-43000	\N	Weekend Roundup: Rows of Red as Bitcoin Taps $43,000	{bitcoin,ethereum}	BeInCrypto	2021-02-28 19:20:11	\N
113	\N	Bitcoin and Open Blockchain Open Topic - February 2021 Livestream aantonop	Bitcoin-and-Open-Blockchain-Open-Topic-February-2021-Livestream-aantonop	\N	Bitcoin and Open Blockchain Open Topic - February 2021 Livestream aantonop	{bitcoin}	aantonop	2021-02-28 19:14:06	\N
114	\N	Bitcoin (BTC) Profit-Taking Slowing According to Glassnode	Bitcoin-BTC-Profit-Taking-Slowing-According-to-Glassnode	\N	Bitcoin (BTC) Profit-Taking Slowing According to Glassnode	{bitcoin}	BeInCrypto	2021-02-28 18:42:46	\N
115	\N	Crypto Bull Mike Novogratz Dramatically Increases Bitcoin Price Forecast for End of 2021	Crypto-Bull-Mike-Novogratz-Dramatically-Increases-Bitcoin-Price-Forecast-for-End-of-2021	\N	Crypto Bull Mike Novogratz Dramatically Increases Bitcoin Price Forecast for End of 2021	{bitcoin}	The Daily Hodl	2021-02-28 18:30:08	\N
116	\N	Tether Being Extorted for $22 Million Worth of Bitcoin	Tether-Being-Extorted-for-22-Million-Worth-of-Bitcoin	\N	Tether Being Extorted for $22 Million Worth of Bitcoin	{bitcoin,tether}	U.Today	2021-02-28 18:25:00	\N
117	\N	Lessons to learn from the Bitcoin rallies of 2013 and 2017	Lessons-to-learn-from-the-Bitcoin-rallies-of-2013-and-2017	\N	Lessons to learn from the Bitcoin rallies of 2013 and 2017	{bitcoin}	AMBCrypto	2021-02-28 18:00:41	\N
118	\N	NBA Top Shot leads NFT explosion with $230M in sales	NBA-Top-Shot-leads-NFT-explosion-with-230M-in-sales	\N	NBA Top Shot leads NFT explosion with $230M in sales	\N	CoinTelegraph	2021-02-28 18:00:00	\N
119	\N	Neuer Ethereum-DeFi-Hack: Schwachstelle ermöglicht Angreifer 14 Millionen-Dollar-Diebstahl	Neuer-Ethereum-DeFi-Hack-Schwachstelle-ermoglicht-Angreifer-14-Millionen-Dollar-Diebstahl	\N	Neuer Ethereum-DeFi-Hack: Schwachstelle ermöglicht Angreifer 14 Millionen-Dollar-Diebstahl	{bitcoin,ethereum}	CoinUpdate.de	2021-02-28 17:54:45	\N
120	\N	Bitcoin Price Analysis: Hope? Bullish Pennant Forming As BTC Plunges To $43K	Bitcoin-Price-Analysis-Hope-Bullish-Pennant-Forming-As-BTC-Plunges-To-43K	\N	Bitcoin Price Analysis: Hope? Bullish Pennant Forming As BTC Plunges To $43K	{bitcoin}	Feed - Cryptopotato.Com	2021-02-28 17:51:57	\N
121	\N	Albtraum Bitcoin-Revolution? – Ökonom warnt vor „extremer Ungleichverteilung“	Albtraum-Bitcoin-Revolution-Okonom-warnt-vor-extremer-Ungleichverteilung	\N	Albtraum Bitcoin-Revolution? – Ökonom warnt vor „extremer Ungleichverteilung“	{bitcoin}	CoinTelegraph DE	2021-02-28 17:22:06	\N
122	\N	After XRP and Litecoin, Flare Plans To Bring Smart Contract Functionality to Stellar	After-XRP-and-Litecoin-Flare-Plans-To-Bring-Smart-Contract-Functionality-to-Stellar	\N	After XRP and Litecoin, Flare Plans To Bring Smart Contract Functionality to Stellar	{xrp,litecoin,stellar}	The Daily Hodl	2021-02-28 17:10:00	\N
123	\N	OLB Group enables crypto payments for thousands of US merchants	OLB-Group-enables-crypto-payments-for-thousands-of-US-merchants	\N	OLB Group enables crypto payments for thousands of US merchants	\N	CoinTelegraph	2021-02-28 17:00:00	\N
124	\N	Was passiert, wenn dem Markt die Bitcoin (BTC) ausgehen?	Was-passiert-wenn-dem-Markt-die-Bitcoin-BTC-ausgehen	\N	Was passiert, wenn dem Markt die Bitcoin (BTC) ausgehen?	{bitcoin}	BTC ECHO	2021-03-01 06:00:00	\N
125	\N	Here’s how the Purpose Bitcoin ETF differs from Grayscale’s GBTC Trust	Heres-how-the-Purpose-Bitcoin-ETF-differs-from-Grayscales-GBTC-Trust	\N	Here’s how the Purpose Bitcoin ETF differs from Grayscale’s GBTC Trust	{bitcoin}	CoinTelegraph	2021-02-28 23:45:00	\N
126	\N	LIVE: End of Crypto Bull Run? Buy the Dip or Wait? BTC, ETH, and Cardano March Predictions!	LIVE-End-of-Crypto-Bull-Run-Buy-the-Dip-or-Wait-BTC-ETH-and-Cardano-March-Predictions	\N	LIVE: End of Crypto Bull Run? Buy the Dip or Wait? BTC, ETH, and Cardano March Predictions!	{cardano}	Ian Balina	2021-02-28 23:16:02	\N
127	\N	Breaking Down Square’s Bitcoin (BTC) Position	Breaking-Down-Squares-Bitcoin-BTC-Position	\N	Breaking Down Square’s Bitcoin (BTC) Position	{bitcoin}	Bitcoinist	2021-02-28 23:00:00	\N
128	\N	Governments Couldn’t Ban Bitcoin Even if They Wanted To	Governments-Couldnt-Ban-Bitcoin-Even-if-They-Wanted-To	\N	Governments Couldn’t Ban Bitcoin Even if They Wanted To	{bitcoin}	The Breakdown	2021-02-28 23:00:00	\N
129	\N	Crypto Exchange Mistakenly Sold Bitcoin for $6,000: Now Requests Users To Return It	Crypto-Exchange-Mistakenly-Sold-Bitcoin-for-6000-Now-Requests-Users-To-Return-It	\N	Crypto Exchange Mistakenly Sold Bitcoin for $6,000: Now Requests Users To Return It	{bitcoin}	Feed - Cryptopotato.Com	2021-02-28 22:35:35	\N
130	\N	Ripple-Backed Developer Launches Proposal To Bring Red-Hot NFTs to XRP Ledger	Ripple-Backed-Developer-Launches-Proposal-To-Bring-Red-Hot-NFTs-to-XRP-Ledger	\N	Ripple-Backed Developer Launches Proposal To Bring Red-Hot NFTs to XRP Ledger	{xrp}	The Daily Hodl	2021-02-28 22:15:35	\N
131	\N	Slam Dunk – NBA wird mit Top Shot zum Marktführer für Krypto-Sammelkarten	Slam-Dunk-NBA-wird-mit-Top-Shot-zum-Marktfuhrer-fur-Krypto-Sammelkarten	\N	Slam Dunk – NBA wird mit Top Shot zum Marktführer für Krypto-Sammelkarten	\N	CoinTelegraph DE	2021-02-28 21:34:00	\N
132	\N	Top 5 cryptocurrencies to watch this week: BTC, BNB, DOT, XEM, MIOTA	Top-5-cryptocurrencies-to-watch-this-week-BTC-BNB-DOT-XEM-MIOTA	\N	Top 5 cryptocurrencies to watch this week: BTC, BNB, DOT, XEM, MIOTA	{bitcoin,polkadot,"binance coin",nem,iota}	CoinTelegraph	2021-02-28 21:22:32	\N
133	\N	HISTORY IS REPEATING!!  BTC indicator showing $350K cycle top	HISTORY-IS-REPEATING-BTC-indicator-showing-350K-cycle-top	\N	HISTORY IS REPEATING!!  BTC indicator showing $350K cycle top	{bitcoin}	FUD TV	2021-02-28 21:22:00	\N
134	\N	Google Finance adds dedicated ‘crypto’ tab featuring Bitcoin, Ether, Litecoin	Google-Finance-adds-dedicated-crypto-tab-featuring-Bitcoin-Ether-Litecoin	\N	Google Finance adds dedicated ‘crypto’ tab featuring Bitcoin, Ether, Litecoin	{litecoin}	CoinTelegraph	2021-02-28 21:15:00	\N
135	\N	Analysis Of A Fed Note: On Preconditions For A General-Purpose Central Bank Digital Currency	Analysis-Of-A-Fed-Note-On-Preconditions-For-A-General-Purpose-Central-Bank-Digital-Currency	\N	Analysis Of A Fed Note: On Preconditions For A General-Purpose Central Bank Digital Currency	\N	Forbes	2021-03-01 03:33:46	\N
136	\N	TA: Bitcoin Price Recovers, Why BTC Could Struggle Near 100 SMA	TA-Bitcoin-Price-Recovers-Why-BTC-Could-Struggle-Near-100-SMA	\N	TA: Bitcoin Price Recovers, Why BTC Could Struggle Near 100 SMA	{bitcoin,near}	NewsBTC	2021-03-01 03:18:53	\N
137	\N	LIVE: March Crypto Price Predictions! Token Metrics AMA	LIVE-March-Crypto-Price-Predictions-Token-Metrics-AMA	\N	LIVE: March Crypto Price Predictions! Token Metrics AMA	\N	Ian Balina	2021-03-01 03:04:47	\N
138	\N	BITCOIN CRASH OVER? BOTTOM IN? SURPRISING BITCOIN NEWS SAYS YES! Bitcoin Analysis Today	BITCOIN-CRASH-OVER-BOTTOM-IN-SURPRISING-BITCOIN-NEWS-SAYS-YES-Bitcoin-Analysis-Today	\N	BITCOIN CRASH OVER? BOTTOM IN? SURPRISING BITCOIN NEWS SAYS YES! Bitcoin Analysis Today	{bitcoin}	The Crypto Lark	2021-03-01 03:03:31	\N
139	\N	Crypto Analyst Points to Little-Known Altcoin With Bullish Potential	Crypto-Analyst-Points-to-Little-Known-Altcoin-With-Bullish-Potential	\N	Crypto Analyst Points to Little-Known Altcoin With Bullish Potential	\N	CryptoGlobe	2021-03-01 02:09:00	\N
140	\N	Crypto Market Forecast: Week of March 1st 2021	Crypto-Market-Forecast-Week-of-March-1st-2021	\N	Crypto Market Forecast: Week of March 1st 2021	{bitcoin,cardano}	Brave New Coin	2021-03-01 01:30:00	\N
141	\N	Top Bitcoin Strategist Calls Market Bottom, Says It’s Time to Buy the Dip	Top-Bitcoin-Strategist-Calls-Market-Bottom-Says-Its-Time-to-Buy-the-Dip	\N	Top Bitcoin Strategist Calls Market Bottom, Says It’s Time to Buy the Dip	{bitcoin}	CryptoGlobe	2021-03-01 01:09:00	\N
142	\N	Fractal Is Replacing Ad Cookies and Why That’s a Big Deal	Fractal-Is-Replacing-Ad-Cookies-and-Why-Thats-a-Big-Deal	\N	Fractal Is Replacing Ad Cookies and Why That’s a Big Deal	\N	Bitcoinist	2021-03-01 07:49:46	\N
143	\N	Bitcoin Weekly Outlook: T-Bonds Threaten Crucial BTC/USD Support	Bitcoin-Weekly-Outlook-T-Bonds-Threaten-Crucial-BTCUSD-Support	\N	Bitcoin Weekly Outlook: T-Bonds Threaten Crucial BTC/USD Support	{bitcoin}	Bitcoinist	2021-03-01 07:39:36	\N
144	\N	Top 5 Things to Check Before Investing in a Layer 1 Protocol	Top-5-Things-to-Check-Before-Investing-in-a-Layer-1-Protocol	\N	Top 5 Things to Check Before Investing in a Layer 1 Protocol	\N	NewsBTC	2021-03-01 07:37:27	\N
145	\N	Stablecoins wachsen massiv: Ein Marktüberblick	Stablecoins-wachsen-massiv-Ein-Marktuberblick	\N	Stablecoins wachsen massiv: Ein Marktüberblick	\N	BTC ECHO	2021-03-01 07:30:00	\N
146	\N	Charted: Cardano (ADA) Holding Crucial Support, Why It Could Surge Again	Charted-Cardano-ADA-Holding-Crucial-Support-Why-It-Could-Surge-Again	\N	Charted: Cardano (ADA) Holding Crucial Support, Why It Could Surge Again	{cardano}	NewsBTC	2021-03-01 07:18:32	\N
147	\N	Bitcoin (BTC) Bounces Back After Decreasing Below $45,000	Bitcoin-BTC-Bounces-Back-After-Decreasing-Below-45000	\N	Bitcoin (BTC) Bounces Back After Decreasing Below $45,000	{bitcoin}	BeInCrypto	2021-03-01 07:02:00	\N
148	\N	Peter Brandt Reveals What It Will Take for Bitcoin to Go Higher	Peter-Brandt-Reveals-What-It-Will-Take-for-Bitcoin-to-Go-Higher	\N	Peter Brandt Reveals What It Will Take for Bitcoin to Go Higher	{bitcoin}	U.Today	2021-03-01 06:31:00	\N
149	\N	High APR for @TrustToken ! #TUSD	High-APR-for-TrustToken-TUSD	\N	High APR for @TrustToken ! #TUSD	{tron}	TRON Twitter	2021-03-01 06:30:19	\N
150	\N	Loopring DEX Launches Layer 2 Bitcoin Trading Pools	Loopring-DEX-Launches-Layer-2-Bitcoin-Trading-Pools	\N	Loopring DEX Launches Layer 2 Bitcoin Trading Pools	{bitcoin,ethereum,maker,loopring}	BeInCrypto	2021-03-01 06:17:00	\N
151	\N	Dubai-Based FD7 Ventures Launches New $250 Million Fund Focused on Cardano and Polkadot	Dubai-Based-FD7-Ventures-Launches-New-250-Million-Fund-Focused-on-Cardano-and-Polkadot	\N	Dubai-Based FD7 Ventures Launches New $250 Million Fund Focused on Cardano and Polkadot	{cardano,polkadot}	U.Today	2021-03-01 06:02:00	\N
152	\N	Déjà vu: Ethereum’s First Month of CME Futures Overwhelmingly Bearish	Deja-vu-Ethereums-First-Month-of-CME-Futures-Overwhelmingly-Bearish	\N	Déjà vu: Ethereum’s First Month of CME Futures Overwhelmingly Bearish	{bitcoin,ethereum}	Feed - Cryptopotato.Com	2021-03-01 05:29:08	\N
153	\N	TA: Ethereum Bears Keep Pushing, Why ETH Could Face Hurdles Near $1,500	TA-Ethereum-Bears-Keep-Pushing-Why-ETH-Could-Face-Hurdles-Near-1500	\N	TA: Ethereum Bears Keep Pushing, Why ETH Could Face Hurdles Near $1,500	{ethereum,near}	NewsBTC	2021-03-01 05:18:57	\N
154	\N	#Binance USDⓈ-M Futures System Upgrade Notice (2021-03-02)	Binance-USDS-M-Futures-System-Upgrade-Notice-2021-03-02	\N	#Binance USDⓈ-M Futures System Upgrade Notice (2021-03-02)	{"binance coin"}	Binance Coin Twitter	2021-03-01 05:12:14	\N
155	\N	BIG Bitcoin Price Bounce Off $43k, Bottom In?	BIG-Bitcoin-Price-Bounce-Off-43k-Bottom-In	\N	BIG Bitcoin Price Bounce Off $43k, Bottom In?	{bitcoin}	Tone Vays	2021-03-01 04:59:32	\N
156	\N	Bitcoin Bottoming Out After Its Worst Weekly Drop Since March: Analyst	Bitcoin-Bottoming-Out-After-Its-Worst-Weekly-Drop-Since-March-Analyst	\N	Bitcoin Bottoming Out After Its Worst Weekly Drop Since March: Analyst	{bitcoin}	NewsBTC	2021-03-01 11:38:14	\N
184	\N	Bitcoin Just Broke $48k - Time for a Quick Update!!!	Bitcoin-Just-Broke-48k-Time-for-a-Quick-Update	\N	Bitcoin Just Broke $48k - Time for a Quick Update!!!	{bitcoin}	Tone Vays	2021-03-01 15:08:19	\N
157	\N	Bitcoin could become the “currency of choice for international trade”, says Citi	Bitcoin-could-become-the-currency-of-choice-for-international-trade-says-Citi	\N	Bitcoin could become the “currency of choice for international trade”, says Citi	{bitcoin}	The Block	2021-03-01 11:37:25	\N
158	\N	$250M Fund to Invest in Polkadot and Cardano Launched in India	250M-Fund-to-Invest-in-Polkadot-and-Cardano-Launched-in-India	\N	$250M Fund to Invest in Polkadot and Cardano Launched in India	{cardano,polkadot}	Feed - Cryptopotato.Com	2021-03-01 11:36:44	\N
159	\N	Altcoins Set To Rebound | Here's What You Need To Know	Altcoins-Set-To-Rebound-Heres-What-You-Need-To-Know	\N	Altcoins Set To Rebound | Here's What You Need To Know	\N	DataDash	2021-03-01 11:34:24	\N
160	\N	Dogecoin will mit Update neu angreifen – SEC knöpft sich Elon Musk vor	Dogecoin-will-mit-Update-neu-angreifen-SEC-knopft-sich-Elon-Musk-vor	\N	Dogecoin will mit Update neu angreifen – SEC knöpft sich Elon Musk vor	{dogecoin}	BTC ECHO	2021-03-01 11:33:00	\N
161	\N	Fantom brushes off network outage with another 30% price surge	Fantom-brushes-off-network-outage-with-another-30-price-surge	\N	Fantom brushes off network outage with another 30% price surge	{fantom}	CoinTelegraph	2021-03-01 11:28:50	\N
162	\N	The Future of Crypto Exchanges Is Decentralized, Says Binance CEO	The-Future-of-Crypto-Exchanges-Is-Decentralized-Says-Binance-CEO	\N	The Future of Crypto Exchanges Is Decentralized, Says Binance CEO	{ethereum,uniswap}	CryptoBriefing	2021-03-01 11:27:39	\N
163	\N	Bitcoin (BTC) Enters Its Most Bearish Month: See Statistics	Bitcoin-BTC-Enters-Its-Most-Bearish-Month-See-Statistics	\N	Bitcoin (BTC) Enters Its Most Bearish Month: See Statistics	{bitcoin}	U.Today	2021-03-01 11:23:44	\N
164	\N	Gut für Ethereum: ETH-Miner akzeptieren kommendes Update doch – 55% dafür	Gut-fur-Ethereum-ETH-Miner-akzeptieren-kommendes-Update-doch-55-dafur	\N	Gut für Ethereum: ETH-Miner akzeptieren kommendes Update doch – 55% dafür	{bitcoin,ethereum,"ethereum classic"}	CoinUpdate.de	2021-03-01 11:23:38	\N
165	\N	Crypto Market Recovery, Stock Market Crash 2021, Buying The Dip, Ethereum ETP & Bitcoin E-Pay	Crypto-Market-Recovery-Stock-Market-Crash-2021-Buying-The-Dip-Ethereum-ETP-Bitcoin-E-Pay	\N	Crypto Market Recovery, Stock Market Crash 2021, Buying The Dip, Ethereum ETP & Bitcoin E-Pay	{bitcoin,ethereum}	The Modern Investor	2021-03-01 11:02:48	\N
166	\N	Bitcoin is Power - Power is Everything (SOB#456)	Bitcoin-is-Power-Power-is-Everything-SOB456	\N	Bitcoin is Power - Power is Everything (SOB#456)	{bitcoin}	aantonop	2021-03-01 11:00:30	\N
167	\N	#502: Robert Breedlove on Bitcoin As The Apex Predator	502-Robert-Breedlove-on-Bitcoin-As-The-Apex-Predator	\N	#502: Robert Breedlove on Bitcoin As The Apex Predator	{bitcoin}	Off the Chain	2021-03-01 11:00:00	\N
168	\N	3 reasons Bitcoin recovered by 8% overnight — Key levels to watch next	3-reasons-Bitcoin-recovered-by-8-overnight-Key-levels-to-watch-next	\N	3 reasons Bitcoin recovered by 8% overnight — Key levels to watch next	{bitcoin}	CoinTelegraph	2021-03-01 11:00:00	\N
169	\N	Rakuten's customers can now use Bitcoin for shopping	Rakutens-customers-can-now-use-Bitcoin-for-shopping	\N	Rakuten's customers can now use Bitcoin for shopping	{bitcoin}	CoinTelegraph	2021-03-01 10:52:37	\N
170	\N	Bitcoin is at a ‘tipping point’ in international trade, Citi says	Bitcoin-is-at-a-tipping-point-in-international-trade-Citi-says	\N	Bitcoin is at a ‘tipping point’ in international trade, Citi says	{bitcoin}	CoinTelegraph	2021-03-01 10:44:42	\N
171	\N	Bullisher Bitcoin-Wochenausblick – Top-Trader zuversichtlich: „Wir sind noch früh dran“	Bullisher-Bitcoin-Wochenausblick-Top-Trader-zuversichtlich-Wir-sind-noch-fruh-dran	\N	Bullisher Bitcoin-Wochenausblick – Top-Trader zuversichtlich: „Wir sind noch früh dran“	{bitcoin}	CoinUpdate.de	2021-03-01 10:38:00	\N
172	\N	Bitcoin Could Become Preferred Currency for International Trade: Citi Analysts	Bitcoin-Could-Become-Preferred-Currency-for-International-Trade-Citi-Analysts	\N	Bitcoin Could Become Preferred Currency for International Trade: Citi Analysts	{bitcoin}	U.Today	2021-03-01 10:33:03	\N
173	\N	Far-Right Website Gab Suffers Data Breach in Major Hack	Far-Right-Website-Gab-Suffers-Data-Breach-in-Major-Hack	\N	Far-Right Website Gab Suffers Data Breach in Major Hack	{bitcoin}	CryptoBriefing	2021-03-01 10:32:42	\N
174	\N	Afrika als Bitcoin-Kontinent - eine unterschätze Krypto-Oase?	Afrika-als-Bitcoin-Kontinent-eine-unterschatze-Krypto-Oase	\N	Afrika als Bitcoin-Kontinent - eine unterschätze Krypto-Oase?	{bitcoin}	BTC ECHO	2021-03-01 10:21:21	\N
175	\N	Bitcoin zeigt wieder Stärke – erneuter Test von 58.000 Dollar jetzt wahrscheinlich	Bitcoin-zeigt-wieder-Starke-erneuter-Test-von-58000-Dollar-jetzt-wahrscheinlich	\N	Bitcoin zeigt wieder Stärke – erneuter Test von 58.000 Dollar jetzt wahrscheinlich	{bitcoin}	CoinUpdate.de	2021-03-01 10:20:41	\N
176	\N	Sony PlayStation 5 Now Used for Ethereum Mining	Sony-PlayStation-5-Now-Used-for-Ethereum-Mining	\N	Sony PlayStation 5 Now Used for Ethereum Mining	{ethereum}	U.Today	2021-03-01 15:42:00	\N
177	\N	WallStreetBets 2.0 Launching On-Chain Investment Pool	WallStreetBets-20-Launching-On-Chain-Investment-Pool	\N	WallStreetBets 2.0 Launching On-Chain Investment Pool	{bitcoin,dogecoin,solana}	CryptoBriefing	2021-03-01 15:41:16	\N
178	\N	BlockFi clients earn record interest payments in February	BlockFi-clients-earn-record-interest-payments-in-February	\N	BlockFi clients earn record interest payments in February	{ethereum}	CoinTelegraph	2021-03-01 15:34:14	\N
179	\N	XRPL Fork CasinoCoin Ledger Abandoned by Its Flagship Project. Here's Why	XRPL-Fork-CasinoCoin-Ledger-Abandoned-by-Its-Flagship-Project-Heres-Why	\N	XRPL Fork CasinoCoin Ledger Abandoned by Its Flagship Project. Here's Why	{xrp}	U.Today	2021-03-01 15:33:47	\N
180	\N	“Wonderful” Shark Tank Investor Shifts Portion of Portfolio To Bitcoin and Ethereum	Wonderful-Shark-Tank-Investor-Shifts-Portion-of-Portfolio-To-Bitcoin-and-Ethereum	\N	“Wonderful” Shark Tank Investor Shifts Portion of Portfolio To Bitcoin and Ethereum	{bitcoin,ethereum}	Bitcoinist	2021-03-01 15:30:41	\N
181	\N	Bitcoin Price Analysis: BTC Skyrockets Over $5K Today – Temp Correction Or $50K Inbound?	Bitcoin-Price-Analysis-BTC-Skyrockets-Over-5K-Today-Temp-Correction-Or-50K-Inbound	\N	Bitcoin Price Analysis: BTC Skyrockets Over $5K Today – Temp Correction Or $50K Inbound?	{bitcoin}	Feed - Cryptopotato.Com	2021-03-01 15:19:42	\N
182	\N	Sentiment Turns Fearful as Bitcoin Recovers Above $48,000	Sentiment-Turns-Fearful-as-Bitcoin-Recovers-Above-48000	\N	Sentiment Turns Fearful as Bitcoin Recovers Above $48,000	{bitcoin}	U.Today	2021-03-01 15:15:09	\N
185	\N	Citibank-Analysten: Bitcoin an einem "Wendepunkt" im internationalen Handel	Citibank-Analysten-Bitcoin-an-einem-Wendepunkt-im-internationalen-Handel	\N	Citibank-Analysten: Bitcoin an einem "Wendepunkt" im internationalen Handel	{bitcoin}	CoinTelegraph DE	2021-03-01 15:07:31	\N
186	\N	Zahlt Kraken eine Wiedergutmachung für den Horror-Crash?	Zahlt-Kraken-eine-Wiedergutmachung-fur-den-Horror-Crash	\N	Zahlt Kraken eine Wiedergutmachung für den Horror-Crash?	{ethereum}	BTC ECHO	2021-03-01 15:04:00	\N
187	\N	Bitcoin Kurs mit Korrektur oder Bärenmarkt?	Bitcoin-Kurs-mit-Korrektur-oder-Barenmarkt	\N	Bitcoin Kurs mit Korrektur oder Bärenmarkt?	{bitcoin}	CryptoMonday	2021-03-01 15:00:55	\N
188	\N	MIT, MicroStrategy, Others, Launch Multi-Year Project to Strengthen Bitcoin	MIT-MicroStrategy-Others-Launch-Multi-Year-Project-to-Strengthen-Bitcoin	\N	MIT, MicroStrategy, Others, Launch Multi-Year Project to Strengthen Bitcoin	{bitcoin}	BTC Manager	2021-03-01 15:00:45	\N
189	\N	Monday meme. Payday Celsians. Enjoy your day.	Monday-meme-Payday-Celsians-Enjoy-your-day	\N	Monday meme. Payday Celsians. Enjoy your day.	{"celsius network"}	Celsius Network Reddit	2021-03-01 15:00:21.391429	\N
190	\N	Bitkom_Block Eventreihe: Eine Einführung in Polkadot (DOT)	Bitkom_Block-Eventreihe-Eine-Einfuhrung-in-Polkadot-DOT	\N	Bitkom_Block Eventreihe: Eine Einführung in Polkadot (DOT)	{polkadot}	BTC ECHO	2021-03-01 14:53:45	\N
191	\N	IOTA And Horizen Partner To Expand IOTA Oracles and Zendoo	IOTA-And-Horizen-Partner-To-Expand-IOTA-Oracles-and-Zendoo	\N	IOTA And Horizen Partner To Expand IOTA Oracles and Zendoo	{horizen,iota}	ZenCash Blog	2021-03-01 14:52:54	\N
192	\N	Indexed Finance Combines DeFi Favorites in New “DEGEN Index”	Indexed-Finance-Combines-DeFi-Favorites-in-New-DEGEN-Index	\N	Indexed Finance Combines DeFi Favorites in New “DEGEN Index”	{uniswap,sushi,ren}	CryptoBriefing	2021-03-01 14:51:57	\N
193	\N	Citibank presents its “bull case” for Bitcoin, but also cautions of risks	Citibank-presents-its-bull-case-for-Bitcoin-but-also-cautions-of-risks	\N	Citibank presents its “bull case” for Bitcoin, but also cautions of risks	\N	CryptoSlate	2021-03-01 14:49:25	\N
194	\N	Bitcoin Poised For ‘Massive Transformation’ Into The Mainstream, Citi Says	Bitcoin-Poised-For-Massive-Transformation-Into-The-Mainstream-Citi-Says	\N	Bitcoin Poised For ‘Massive Transformation’ Into The Mainstream, Citi Says	{bitcoin}	Forbes	2021-03-01 14:47:51	\N
195	\N	Twitter to Pull a MicroStrategy and Buy Bitcoin? The Firm Plans a $1.25 Billion Convertible Notes Offering	Twitter-to-Pull-a-MicroStrategy-and-Buy-Bitcoin-The-Firm-Plans-a-125-Billion-Convertible-Notes-Offering	\N	Twitter to Pull a MicroStrategy and Buy Bitcoin? The Firm Plans a $1.25 Billion Convertible Notes Offering	{bitcoin}	Feed - Cryptopotato.Com	2021-03-01 14:38:16	\N
196	\N	Crypto Pepes: What does the frog meme?		https://cointelegraph.com/magazine/wp-content/uploads/2021/03/magazine_Pepe.jpg		{features}	Cointelegraph By Andrew Fenton	2021-03-05 14:40:48	\N
197	\N	‘Better as friends’: DeFi protocols Yearn and Cover announce cessation of merger	The relationship is "c-over" between Yearn Finance and Cover, an emotional split related to a new protocol from Cover developers.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvZmE5NGE1YjAtNDc2Yy00NzcxLThlNTAtOWU2ZTBmM2NjZTc2LmpwZw==.jpg	The relationship is "c-over" between Yearn Finance and Cover, an emotional split related to a new protocol from Cover developers.	{cover,yearn,insurance,defi,dao,merger}	Cointelegraph By Andrew Thurman	2021-03-05 14:54:23	\N
198	\N	No more ‘Bitcoin effect?’ MicroStrategy stock falls by 50% in 17 days	The excitement around Bitcoin has spilled over beyond spot price, data shows, with MSTR going from above $1,300 to $629 in just 17 days.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvODQyMGM3NjItZTUxYi00YTlhLWE3MGUtNzcwMmNhMzQxMzU4LmpwZw==.jpg	The excitement around Bitcoin has spilled over beyond spot price, data shows, with MSTR going from above $1,300 to $629 in just 17 days.	{bitcoin,microstrategy,"arthur hayes","stock price","btc price",mstr}	Cointelegraph By William Suberg	2021-03-05 15:30:00	\N
199	\N	GameStop tale exposes regulatory paternalism and DeFi’s true value	GameStop brings forward the prospect of a paradigm shift that challenges existing regulations: decentralization.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDIvMzA2NzNiYTQtYTQ0NS00YmU4LTk2YWYtNDdlNDdlMjZiNGRkLmpwZw==.jpg	GameStop brings forward the prospect of a paradigm shift that challenges existing regulations: decentralization.	{}	Cointelegraph By Sarah H. Brennan	2021-03-05 15:36:11	\N
200	\N	The Flash Mint is here: WETH10 turbocharges the flash loan concept	The newest Wrapped Ether has an extensive list of improvements, including the anticipated flash mint feature.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvYWU1OWI4NGMtMDU3ZS00ZmVlLTk4NjItNGRjMWI1YmU0NWFiLmpwZw==.jpg	The newest Wrapped Ether has an extensive list of improvements, including the anticipated flash mint feature.	{"flash mint",defi,"flash loan"}	Cointelegraph By Andrey Shevchenko	2021-03-05 16:41:58	\N
201	\N	Bitcoin ETF may come to US, but not all crypto investors think it’s needed	As Bitcoin ETFs launch in Canada, an approval from U.S. authorities appears to be closer than ever before as naysayers start to run out of reasons to deny it.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvYjZlNTliODgtZDIyMi00YTg1LWIyMzQtMzI0MWUwMjExMDM3LmpwZw==.jpg	As Bitcoin ETFs launch in Canada, an approval from U.S. authorities appears to be closer than ever before as naysayers start to run out of reasons to deny it.	{etf,canada,"united states"}	Cointelegraph By Andrew Singer	2021-03-05 17:07:00	\N
202	\N	Major Ethereum gas fee overhaul EIP-1559 scheduled for July	Users and investors delight as EIP-1559 is scheduled for July, but ETH miners are less than thrilled. 	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvMmMyODQzNjUtYmVhZC00NjlmLWIyMWMtMDdmMTRkMDU3OGE3LmpwZw==.jpg	Users and investors delight as EIP-1559 is scheduled for July, but ETH miners are less than thrilled. 	{ethereum,eth,"gas fees",miners,eip-1559}	Cointelegraph By Andrew Thurman	2021-03-05 17:22:38	\N
251	\N	Partly cloudy: How blockchain can become a force of nature	Making blockchain a cloud-native concept may lead to the next leap in trustworthy applications for real people.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvMWEzZjcwOGUtYzkzNS00ODc3LWFhYmItOGQyNWZjOTAxNmY5LmpwZw==.jpg	Making blockchain a cloud-native concept may lead to the next leap in trustworthy applications for real people.	{"cloud services",applications,cryptocurrencies,adoption}	Cointelegraph By Bart Wyatt	2021-03-09 15:17:00	\N
203	\N	Big Tech sell-off and rising Treasury yield pin Bitcoin price below $50K	A sharp spike in the 10-year U.S. Treasury yield and an extended Big Tech sell-off is weighing on cryptocurrency prices as investors flee risk-on assets.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvNGY0YTFlN2ItNDljMy00NTE1LWFjZTYtM2JjZjdlY2NlOTYyLmpwZw==.jpg	A sharp spike in the 10-year U.S. Treasury yield and an extended Big Tech sell-off is weighing on cryptocurrency prices as investors flee risk-on assets.	{cryptocurrencies,"bitcoin price",markets,"market update","ether price"}	Cointelegraph By Jordan Finneseth	2021-03-05 18:53:35	\N
204	\N	McAfee faces crypto-related fraud charges from NY court 	Meanwhile, the anti-virus mogul is still imprisoned in Spain. 	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvZTMzMDI5Y2YtMjBkYy00N2Q1LTg2M2MtMDAyYzhiZWU3NThjLmpwZw==.jpg	Meanwhile, the anti-virus mogul is still imprisoned in Spain. 	{ico,"john mcafee",mcafee,"department of justice","united states"}	Cointelegraph By Benjamin Pirus	2021-03-05 19:13:26	\N
205	\N	Pricing the hype: Crypto companies valued at billions as market booms	Crunching the numbers: Analysts and industry experts weigh in on crypto firms like Coinbase and Kraken being valued in the billions. 	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvZTMyYmJkOTktOTAyMS00YmUyLTljYjktNjMyOGRjNWRmNzg2LmpwZw==.jpg	Crunching the numbers: Analysts and industry experts weigh in on crypto firms like Coinbase and Kraken being valued in the billions. 	{kraken,coinbase,ipo}	Cointelegraph By Gareth Jenkinson	2021-03-05 19:14:00	\N
206	\N	Is Bitcoin a Ponzi scheme? Pick your side in the latest Cointelegraph Crypto Duel!	Kraken Bitcoin strategist Pierre Rochard debates professor of computer science Jorge Stolfi on whether Bitcoin meets the definition of a Ponzi scheme. 	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvYTdkZTM0YjQtMzdkNS00ZWQwLTlkMDktYzZjZjQyMmJkM2YxLmpwZw==.jpg	Kraken Bitcoin strategist Pierre Rochard debates professor of computer science Jorge Stolfi on whether Bitcoin meets the definition of a Ponzi scheme. 	{"ponzi scheme","bitcoin price","bitcoin mining",investments,cash,mining,"fiat money"}	Cointelegraph By Marco Castrovilli	2021-03-05 19:30:00	\N
207	\N	PAID Network exploiter nets $3 million in infinite mint attack	After an attack at one point worth nearly $180 million, community members are left wondering if the exploit is a "rugpull" or a security lapse.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvN2NiMjA2NzEtZjI5OC00N2NmLWE1MzItNWM0OGMxMGUxZjg0LmpwZw==.jpg	After an attack at one point worth nearly $180 million, community members are left wondering if the exploit is a "rugpull" or a security lapse.	{hack,exploit,"paid network","infinite mint",cover}	Cointelegraph By Andrew Thurman	2021-03-05 19:58:35	\N
208	\N	Price analysis 3/5: BTC, ETH, ADA, BNB, DOT, XRP, UNI, LTC, LINK, BCH	Selling pressure from global equities markets continues to weigh on Bitcoin price as traders endeavor to flip the $50,000 level back to support. 	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvOTFjMGEzZmQtYjI5ZC00MjdiLThjZDktOTY0MDI5NzJjNWM4LmpwZw==.jpg	Selling pressure from global equities markets continues to weigh on Bitcoin price as traders endeavor to flip the $50,000 level back to support. 	{markets,bitcoin,ethereum,cardano,"binance coin",polkadot,ripple,uniswap,litecoin,chainlink,"bitcoin cash","price analysis"}	Cointelegraph By Rakesh Upadhyay	2021-03-05 21:04:27	\N
209	\N	NFT hype will calm, but the concept won't disappear, MEW founder speculates 	Nonfungible tokens are all the rage right now, but how long will the fanfare last? 	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvNzkwNWJjMzctZjdhNC00ZDIyLThhYzAtZDNhNGQ1NjliNjY0LmpwZw==.jpg	Nonfungible tokens are all the rage right now, but how long will the fanfare last? 	{nft}	Cointelegraph By Benjamin Pirus	2021-03-05 21:41:48	\N
210	\N	Traders speculate that Bitcoin’s price may continue to trade sideways for now	CryptoWendyO and Cheds weigh in on where Bitcoin's price could head next.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvMWU4MGI2NWMtMjdjMC00NTE3LThmNTAtYmI0MDJiOTljYzhiLmpwZw==.jpg	CryptoWendyO and Cheds weigh in on where Bitcoin's price could head next.	{"bitcoin price"}	Cointelegraph By Benjamin Pirus	2021-03-05 22:06:49	\N
211	\N	99% gone in 60 seconds: How a Polkadot trader may have crashed DOT futures	Polkadot (DOT) futures at Binance flash-crashed by 99.5%, potentially generating an $8.3-million profit for the ‘trader’ if they used this clever strategy.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvNWRjN2YzOTctOGJjYS00Mjk4LWEwZDUtMzgyZDYzNTNlNmYwLmpwZw==.jpg	Polkadot (DOT) futures at Binance flash-crashed by 99.5%, potentially generating an $8.3-million profit for the ‘trader’ if they used this clever strategy.	{markets,"market analysis",polkadot,"dot futures"}	Cointelegraph By Marcel Pechman	2021-03-05 22:30:00	\N
212	\N	Law Decoded: Closing remarks on the future of crypto law, March 5	The final Law Decoded moves away from specific news to reflect on the biggest legal issues facing crypto. 	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvOGE5YTlmYzgtOTY0My00OTYzLTk0YzgtYjJkYjEyYzE0Y2NjLmpwZw==.jpg	The final Law Decoded moves away from specific news to reflect on the biggest legal issues facing crypto. 	{ico,sec,aml,cbdc,defi,law,government}	Cointelegraph By Kollen Post	2021-03-05 23:07:38	\N
213	\N	3 million active users help lift Audius (AUDIO) to a new all-time high 	Audius price hit a new all-time high after the decentralized music streaming platform surpassed 3 million active users and developers hinted at future NFT integrations.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvOGM5OTc0MzMtZDYzNy00MzY1LTlmOTQtNTBmYjJmNmY2MTBiLmpwZw==.jpg	Audius price hit a new all-time high after the decentralized music streaming platform surpassed 3 million active users and developers hinted at future NFT integrations.	{music,streaming,cryptocurrencies,markets,dex,"altcoin watch",nft}	Cointelegraph By Jordan Finneseth	2021-03-06 00:00:00	\N
214	\N	Thailand’s crypto market seeks clearer regulations as industry interest peaks	The Thai SEC says the total number of crypto accounts rose by nearly 300% over the course of the last 180 days.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvMjY1NzA5ODUtM2UyMi00OGRmLTkwMzQtYTY3ZTdiOGIyMDZlLmpwZw==.jpg	The Thai SEC says the total number of crypto accounts rose by nearly 300% over the course of the last 180 days.	{thailand,adoption,regulation}	Cointelegraph By Shiraz Jagati	2021-03-06 06:53:22	\N
215	\N	DeFi will bring a new golden age for the film industry	There are opportunities to be leveraged in bringing DeFi to the film industry, making the investing process easier and more transparent.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDIvMWUxNmZhYmMtNjQ4NC00OWFjLWE4N2MtYjgyNjBjOWQ4YmJiLmpwZw==.jpg	There are opportunities to be leveraged in bringing DeFi to the film industry, making the investing process easier and more transparent.	{defi,decentralization,movie,hollywood}	Cointelegraph By Gagan Grewal	2021-03-06 07:32:00	\N
216	\N	The United Arab Emirates’ green digitization vision	The COVID-19 pandemic has revealed the need for sustainable digitization of the economy, and the UAE is accepting the challenge.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvZmUwODM3ZTEtMmY3MC00ZDY2LWFiODgtODkwNjRmZjk1ODA0LmpwZw==.jpg	The COVID-19 pandemic has revealed the need for sustainable digitization of the economy, and the UAE is accepting the challenge.	{}	Cointelegraph By Selva Ozelli	2021-03-06 11:27:00	\N
217	\N	New York governor Cuomo reveals COVID-19 pilot built on IBM blockchain	Blockchain could play a crucial role in COVID-19 vaccination management as the globe starts to emerge from the crisis.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvNzQzNGJiYTEtZDk2Yy00MGRkLWEyNGUtNWQwMDJhOTU3NmQ1LmpwZw==.jpg	Blockchain could play a crucial role in COVID-19 vaccination management as the globe starts to emerge from the crisis.	{}	Cointelegraph By Rachel Wolfson	2021-03-06 13:07:00	\N
218	\N	Dev says $31 million Meerkat Finance exploit was a ‘test’; will return funds 	DeFi is “flourished by human greed,” according to a developer for the exploited project.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvZDAwY2YxZWUtNWYyYS00YTJjLTg5ZmMtN2E3MzVkZjdmYmI2LmpwZw==.jpg	DeFi is “flourished by human greed,” according to a developer for the exploited project.	{"meerkcat finance","binance smart chain",defi,hack,rugpull,exploit}	Cointelegraph By Andrew Thurman	2021-03-06 14:46:24	\N
219	\N	Bitcoin traders worry as BTC price remains pinned below $50K	The price of Bitcoin remains stuck in a downtrend after failing to close above $50,000. 	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvN2U1YmUzMDYtZTQxYy00M2I1LWI1MTEtY2NkMTZhODgwYzkwLmpwZw==.jpg	The price of Bitcoin remains stuck in a downtrend after failing to close above $50,000. 	{bitcoin,"btc price","treasury yields",dollar}	Cointelegraph By Allen Scott	2021-03-06 15:31:35	\N
220	\N	Ethereum fees are skyrocketing — But traders have alternatives	The issue of scalability is vital if blockchain and decentralized applications are to accommodate a massive user base.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvY2U5NDI2N2ItMjUzMC00MjhjLWIxNDktOWRkM2Q1OWJlMzA1LmpwZw==.jpg	The issue of scalability is vital if blockchain and decentralized applications are to accommodate a massive user base.	{algorand,"ethereum 2.0",fees}	Cointelegraph By Jay Hao	2021-03-06 15:37:00	\N
221	\N	No crypto ban in India: Finance Minister predicts “very calibrated” stance 	No blanket ban on the horizon, with an open “window” for experimentation in India, says Nirmala Sitharaman.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvOTVlYjgyNjgtYWMyMS00ZTExLWFiM2UtZDA5ZjliZDMzODE1LmpwZw==.jpg	No blanket ban on the horizon, with an open “window” for experimentation in India, says Nirmala Sitharaman.	{india,"crypto ban",regulation,innovation,"reserve bank of india"}	Cointelegraph By Andrew Thurman	2021-03-06 15:53:51	\N
222	\N	Apple Pay integration and Staking 3.0 launch push COTI price to a new high	COTI price hit a new all-time high following the roll-out of Staking 3.0 and a Simplex integration that allows Apple Pay users to purchase the token.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvYjI1ZDc2NmYtNGQ2ZS00Mjc4LWI3MGItYzg1MzFmMmU4MjYzLmpwZw==.jpg	COTI price hit a new all-time high following the roll-out of Staking 3.0 and a Simplex integration that allows Apple Pay users to purchase the token.	{visa,mastercard,payments,enterprise,proof-of-stake,cryptocurrencies,markets,adoption,fintech,"altcoin watch"}	Cointelegraph By Jordan Finneseth	2021-03-06 18:24:37	\N
223	\N	Basketball billionaires form NBA blockchain use case committee	Mark Cuban and Vivek Ranadive headline the list of owners exploring blockchain use cases for the NBA	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvZjVhZDA2M2QtYjM4Yi00N2VjLWE2MWMtOTkwMTM4ZGQ3MWIxLmpwZw==.jpg	Mark Cuban and Vivek Ranadive headline the list of owners exploring blockchain use cases for the NBA	{nba,ticketing,"nba topshot",collectibles,"mark cuban"}	Cointelegraph By Andrew Thurman	2021-03-06 19:26:30	\N
224	\N	Bitcoin nerves, Tesla told to dump crypto, NFT madness: Hodler’s Digest, Feb. 28–March 6		https://cointelegraph.com/magazine/wp-content/uploads/2021/03/28feb.jpg		{hodler's,"hodler's digest"}	Cointelegraph By Editorial Staff	2021-03-06 20:39:22	\N
225	\N	What Ethereum killer? On-chain data shows competitor networks are still behind	Critics say Ethereum’s soaring gas fees will cause the project to fall victim to its competitor blockchains but on-chain data suggests otherwise.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvMzFkZGI1YmQtZmRiZC00OGE2LTkwNzMtMjI3ZTM1OGZmYzU0LmpwZw==.jpg	Critics say Ethereum’s soaring gas fees will cause the project to fall victim to its competitor blockchains but on-chain data suggests otherwise.	{markets,"market analysis","ethereum transactions",eth,cardano}	Cointelegraph By Marcel Pechman	2021-03-06 21:00:00	\N
226	\N	Fetch.ai (FET) hits a 2-year high after DeFi integration and Bosch partnership	Strong fundamentals, high-profile partnerships and a pivot toward DeFi back FET's rally to a multi-year high. 	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvNDQ3ODFmY2EtNjcwMC00ZTJkLThjODgtNzE3ODQ3NDZmZjFhLmpwZw==.jpg	Strong fundamentals, high-profile partnerships and a pivot toward DeFi back FET's rally to a multi-year high. 	{ai,cryptocurrencies,markets,defi}	Cointelegraph By Jordan Finneseth	2021-03-07 00:30:29	\N
227	\N	DeFi summer 2.0? 'Gen 2' tokens on a tear amid wider market slump	Decentralized finance 'Gen2' tokens sprint forward as the broader DeFi market gasps for air	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvOWUwMmI1ODItZDAzNy00YWY5LWE4N2MtZGQ4YTQ1M2M2YTNmLmpwZw==.jpg	Decentralized finance 'Gen2' tokens sprint forward as the broader DeFi market gasps for air	{defi,"gen 2",yields,"yield farming","recursive price pumpamentals"}	Cointelegraph By Andrew Thurman	2021-03-07 00:53:16	\N
228	\N	Decentralized finance may be the future, but education is still lacking	Education and accessibility are crucial to make DeFi more accessible to the upcoming inflow of retail investors.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvYWUyYTE5MmEtZGI2Ni00NThiLTkyYjgtYTgyYmIwZDczMDE4LmpwZw==.jpg	Education and accessibility are crucial to make DeFi more accessible to the upcoming inflow of retail investors.	{adoption,education,defi,"decentralized finance","future of money"}	Cointelegraph By Piers Ridyard	2021-03-07 07:32:00	\N
229	\N	Ethereum to roll out Berlin upgrade with 4 EIPs 	The mainnet launch, which is expected to take place on Aprril 14, incorporates four Ethereum Improvement Protocols. 	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvOTU1MWZiOGEtYTI2Ni00MzYwLWFmNWYtZDg1NjdhYWNiNjllLmpwZw==.jpg	The mainnet launch, which is expected to take place on Aprril 14, incorporates four Ethereum Improvement Protocols. 	{cryptocurrencies,"ethereum 2.0",developers,"ether price"}	Cointelegraph By Sam Bourgi	2021-03-08 20:30:00	\N
230	\N	PayPal purchases digital asset security firm Curv	The platform said it plans to complete the acquisition before the third quarter of 2021.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvNjYzZjA3Y2EtOGY5MC00MjkwLWE4NmYtYjQzMDAyM2E0YWI1LmpwZw==.jpg	The platform said it plans to complete the acquisition before the third quarter of 2021.	{business,paypal,payments,"crypto custody",israel,curv}	Cointelegraph By Turner Wright	2021-03-08 21:00:00	\N
231	\N	Pro traders avoid Bitcoin longs while cautiously watching DXY strengthen	Large corporations are buying Bitcoin at an accelerating pace, but pro traders are reluctant to open BTC longs while the dollar index strengthens.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvZDE2OTYyZTMtYjMxNS00N2UyLTg2YTctNzZkMWM5YjYyZWNhLmpwZw==.jpg	Large corporations are buying Bitcoin at an accelerating pace, but pro traders are reluctant to open BTC longs while the dollar index strengthens.	{markets,"market analysis","btc options","btc futures"}	Cointelegraph By Marcel Pechman	2021-03-08 22:00:00	\N
232	\N	Decentralized esports tournament series looks to bring traditional gamers to crypto	Polyient Games co-founder Craig Russo referred to the partnership as "a major step towards bringing mainstream adoption" to the crypto industry.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvNzU0NTg3Y2YtYzU5OC00ZWNkLTk2ZTItYTBkYTMxNWY0NDI0LmpwZw==.jpg	Polyient Games co-founder Craig Russo referred to the partnership as "a major step towards bringing mainstream adoption" to the crypto industry.	{technology,gaming,games,esports,"blockchain games",cryptocurrencies}	Cointelegraph By Turner Wright	2021-03-08 22:18:24	\N
233	\N	Chiliz (CHZ) rallies 60% to a $1B market cap as fan token offerings expand	The success of the AC Milan Fan Token launch and plans to expand to the United States support Chiliz's 1,000% rally over the past month. 	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvMmFmZGU3MTUtNjllOS00M2ZiLThlNjUtZDAyMWNmZmRmZmFlLmpwZw==.jpg	The success of the AC Milan Fan Token launch and plans to expand to the United States support Chiliz's 1,000% rally over the past month. 	{sport,esports,cryptocurrencies,markets,"cryptocurrency exchange",binance,"altcoin watch"}	Cointelegraph By Jordan Finneseth	2021-03-08 23:15:00	\N
234	\N	NFT-related altcoins hit new highs after Bitcoin price rallies to $53K	Bitcoin's daily close above the key $52,000 level signals that bulls intend to make a second attempt at securing a new all-time high. 	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvMGZmMzFmOTUtNTA5Yi00MWNlLTgyOWUtZmRiNDRmM2ZkNmYxLmpwZw==.jpg	Bitcoin's daily close above the key $52,000 level signals that bulls intend to make a second attempt at securing a new all-time high. 	{markets,"market update",cryptocurrencies,"bitcoin price","ether price"}	Cointelegraph By Jordan Finneseth	2021-03-09 02:51:09	\N
235	\N	Ripple and MoneyGram to 'wind down' partnership	While the partnership between Ripple and MoneyGram has been officially terminated, the two firms have expressed they are open to resuming collaboration in the future. 	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvZjRlMGIwMTgtMzkxNC00MTVkLWI0N2ItMjBmNGE5NmE5NGUzLmpwZw==.jpg	While the partnership between Ripple and MoneyGram has been officially terminated, the two firms have expressed they are open to resuming collaboration in the future. 	{ripple,xrp,moneygram,mgi}	Cointelegraph By Martin Young	2021-03-09 03:02:44	\N
236	\N	Glassnode: $47k showed strongest on-chain support since BTC broke $11k 	Approximately 6.5% of Bitcoin’s circulating supply moved on-chain while the markets found support at $47k, representing “one of the largest on-chain BTC accumulation levels” ever.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvMWE3NmZkMDItMTA2Yy00ZTc5LWIzYzctZmI3NjEwZWViMzU2LmpwZw==.jpg	Approximately 6.5% of Bitcoin’s circulating supply moved on-chain while the markets found support at $47k, representing “one of the largest on-chain BTC accumulation levels” ever.	{news,glassnode,"on-chain support",bitcoin,accumulation}	Cointelegraph By Joshua Mapperson	2021-03-09 03:06:35	\N
237	\N	Former DC comic book artist fetches $1.85M auctioning Wonder Woman NFTs	Former DC comic artist, José Delbo, made $1.85 million in four days auctioning 914 NFTs depicting the popular fictional heroine Wonder Woman. 	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvNjRmMDU2OTgtNzBiZC00ZjhmLWE0NTUtNTgwZGFjNmI5NWVmLmpwZw==.jpg	Former DC comic artist, José Delbo, made $1.85 million in four days auctioning 914 NFTs depicting the popular fictional heroine Wonder Woman. 	{"wonder woman","josé delbo",nft,auction}	Cointelegraph By Brian Quarmby	2021-03-09 03:38:29	\N
238	\N	Southeast Asia's first Bitcoin fund launches to meet local institutional demand	The BCMG Genesis Bitcoin Fund-I will be available to accredited Asian investors. 	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvYWRhOThkNTQtM2Q4YS00ZDdkLWI2ODktYmVkYzJjMmNiNGMwLmpwZw==.jpg	The BCMG Genesis Bitcoin Fund-I will be available to accredited Asian investors. 	{bcmg,"bitcoin fund","southeast asia",malaysia}	Cointelegraph By Martin Young	2021-03-09 04:05:09	\N
239	\N	NuCypher and Keep Network propose ‘world’s first decentralized protocol merger’	Data encryption and protection protocols Keep Network and NuCyper have announced plans to integrate around a new network dubbed “Keanu.” 	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvODYwYjdmMmMtM2E5Ni00NDNjLWJlMzAtOGFlMDcxYTk1MjY1LmpwZw==.jpg	Data encryption and protection protocols Keep Network and NuCyper have announced plans to integrate around a new network dubbed “Keanu.” 	{"keep network",nucypher,keanu}	Cointelegraph By Samuel Haig	2021-03-09 04:58:46	\N
240	\N	South African crypto firms warn opaque regulations are harming the industry	South African crypto firms are threatening to move operations abroad if lawmakers don’t provide clear regulatory guidelines. 	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvM2QwN2U5NDItOTUxZC00ZTJmLWE4MzctZmQyNTQxMWM5NWJlLmpwZw==.jpg	South African crypto firms are threatening to move operations abroad if lawmakers don’t provide clear regulatory guidelines. 	{"south africa","crypto firms","crypto regulations"}	Cointelegraph By Brian Quarmby	2021-03-09 05:31:09	\N
241	\N	Bitcoin price cracks major resistance as analyst eyes $70K 'destiny'	An overnight move higher sees local highs of $54,500 and fresh hope that support levels will now focus on $50,000.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvZjI3YWFiYTctODQzYy00OGIwLTljYTItNzU0OGZhMzBlZGVhLmpwZw==.jpg	An overnight move higher sees local highs of $54,500 and fresh hope that support levels will now focus on $50,000.	{bitcoin,"btc price"}	Cointelegraph By William Suberg	2021-03-09 07:35:29	\N
242	\N	Furucombo to issue iouCOMBO tokens to repay victims of $15M exploit	Hacked DeFi service Furucombo will issue "iou" tokens as part of its compensation plan to repay 22 affected users. 	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvYWEzOThhMTYtYTE2OC00YWQ5LTk2ZDItMGU2ZjRjNjRmNGQwLmpwZw==.jpg	Hacked DeFi service Furucombo will issue "iou" tokens as part of its compensation plan to repay 22 affected users. 	{hackers,hacks,defi,"decentralized exchange","decentralized finance",erc-20,tokens,stablecoin}	Cointelegraph By Helen Partz	2021-03-09 08:00:00	\N
243	\N	Coinbase reportedly hits pre-IPO valuation of $100 billion in private auction 	The major exchange is inching closer to a public offering. 	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvMmY4YzkzMmYtODE2MC00NDBjLWFhYjMtZWIxZDlhOWUwNjA0LmpwZw==.jpg	The major exchange is inching closer to a public offering. 	{cryptocurrencies,coinbase,ipo,nasdaq}	Cointelegraph By Greg Thomson	2021-03-09 09:21:09	\N
244	\N	Korean crypto exchange Bithumb toughens up its Anti-Money Laundering measures	The operator of South Korean cryptocurrency exchange Bithumb has placed restrictions on trading accounts registered in jurisdictions that are deemed to be too lax in curbing money laundering.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvMDkwZDdkN2UtNTMwNi00NGQ3LTgyY2YtNDQ4YmYzYjIyNDJlLmpwZw==.jpg	The operator of South Korean cryptocurrency exchange Bithumb has placed restrictions on trading accounts registered in jurisdictions that are deemed to be too lax in curbing money laundering.	{"south korea",bithumb,aml,kyc,"counter terrorism financing"}	Cointelegraph By Marie Huillet 	2021-03-09 09:34:08	\N
245	\N	Major Swiss retailers set to debut Bitcoin gift cards	Manor and Valora are leveraging the current crypto hype to sell Bitcoin gift cards in their stores across Switzerland.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvZGI4OWEyY2YtZDM3Yy00ZTk0LWFiZWUtYzYwMjlmMTFlYzBkLmpwZw==.jpg	Manor and Valora are leveraging the current crypto hype to sell Bitcoin gift cards in their stores across Switzerland.	{bitcoin,"gift cards"}	Cointelegraph By Osato Avan-Nomayo	2021-03-09 10:04:21	\N
246	\N	Bitcoin whales 'bought the dip' as orders for $100K or more hit all-time highs	There's no shortage of demand for Bitcoin, even at $50,000, as big buyers dwarf smallholders in the latest stage of the bull run.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvOTRiMTQyMDgtN2YyMC00ZDg3LWI4ZmItYjY5MWY3MTY3NjczLmpwZw==.jpg	There's no shortage of demand for Bitcoin, even at $50,000, as big buyers dwarf smallholders in the latest stage of the bull run.	{bitcoin,"btc price",whales}	Cointelegraph By William Suberg	2021-03-09 10:35:15	\N
247	\N	Decentraland’s MANA token hits new ATH with Atari set to build in-world casino 	MANA's surge was followed by Atari's ATRI token, and Decentral Games' DG token — all three of which soared to new all-time highs in the wake of the news.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvZWZjYzZiZWQtMjU4MS00MThiLTllYzMtNGQ2MzYwYzk0NDQ2LmpwZw==.jpg	MANA's surge was followed by Atari's ATRI token, and Decentral Games' DG token — all three of which soared to new all-time highs in the wake of the news.	{games,gambling,casino,"virtual property"}	Cointelegraph By Greg Thomson	2021-03-09 12:16:33	\N
248	\N	Parity Technologies to propose parachain governance framework for Polkadot 	The development arm behind Polkadot plans to develop and propose its first common-good parachain.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvYmI0ZDc2OGQtNjY0Yi00NmUwLThmZTctNGEzZGY1Y2E1ZjQyLmpwZw==.jpg	The development arm behind Polkadot plans to develop and propose its first common-good parachain.	{polkadot,developers,cryptocurrencies}	Cointelegraph By Sam Bourgi	2021-03-09 12:30:00	\N
249	\N	Mentorship, inclusivity and education will encourage more women to enter crypto	Leading women in blockchain and crypto speak out on important issues on International Women’s Day.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvNTNhNGM0YWEtYjRlMS00MThmLTgwMmEtZjAyZTkyNDM3NTYwLmpwZw==.jpg	Leading women in blockchain and crypto speak out on important issues on International Women’s Day.	{women,adoption,education}	Cointelegraph By Rachel Wolfson	2021-03-09 13:57:00	\N
250	\N	Huobi and Seychelles regulators can’t agree on where the exchange is registered	The major crypto exchange has long claimed registration in the famous offshoring haven.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvN2U5YThkMmQtMWYwMi00NzdhLWJjOTQtMzU4Njg0NjQzYmJjLmpwZw==.jpg	The major crypto exchange has long claimed registration in the famous offshoring haven.	{cryptocurrencies,"cryptocurrency exchange",huobi,government}	Cointelegraph By Helen Partz	2021-03-09 15:02:27	\N
252	\N	Bitmain accused of illegally poaching engineers by officials in Taiwan	Bitcoin miner maker Bitmain is reportedly in hot water in Taiwan amid the backdrop of the ongoing U.S.-China tech tussle.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvYzg2NTgwOGMtNjk0Yi00ZWI1LWI1YzYtYmI3NTJkZWQwMzNhLmpwZw==.jpg	Bitcoin miner maker Bitmain is reportedly in hot water in Taiwan amid the backdrop of the ongoing U.S.-China tech tussle.	{bitcoin,bitmain,"bitcoin mining",taiwan}	Cointelegraph By Osato Avan-Nomayo	2021-03-09 15:22:50	\N
253	\N	Bybit appoints new general counsel to lead global cryptocurrency compliance 	Daniel Lim will lead Bybit's legal and compliance team, following an appointment hot on the heels of Bybit's U.K. shutdown.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvY2FmOTU1N2YtM2JmMC00ODAzLTgxYjQtNjZiNmU3Njc2NzhjLmpwZw==.jpg	Daniel Lim will lead Bybit's legal and compliance team, following an appointment hot on the heels of Bybit's U.K. shutdown.	{cryptocurrencies,"cryptocurrency exchange","united kingdom",singapore,derivatives,ban}	Cointelegraph By Greg Thomson	2021-03-09 15:57:27	\N
254	\N	Billionaire investor Mark Cuban to talk crypto on Blockchain &amp; Booze tonight	Billionaire investor Mark Cuban is set for a virtual sit down to discuss what he finds exciting in the crypto space, perhaps over a few beers. And it's live on Cointelegraph's Twitter feed.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvMmY5NTM3MTAtMmI2MS00OGFhLWE2MjktZDA1NTQ2MmE1YTE3LmpwZw==.jpg	Billionaire investor Mark Cuban is set for a virtual sit down to discuss what he finds exciting in the crypto space, perhaps over a few beers. And it's live on Cointelegraph's Twitter feed.	{"mark cuban",crypto,nft,blockchain,cointelegraph}	Cointelegraph By Osato Avan-Nomayo	2021-03-09 16:56:46	\N
255	\N	HODLer claims to have used crypto profits for father's cancer treatment	A Redditor reportedly recommended an acquaintance invest in ADA, XDC, GLM, NEXO and ETH back in 2019.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvYzlhZTdmMGUtNjg3ZC00MmU2LTkwY2YtYjQyZjgxODU2NWE3LmpwZw==.jpg	A Redditor reportedly recommended an acquaintance invest in ADA, XDC, GLM, NEXO and ETH back in 2019.	{business,"united kingdom",medicine,hodl,investments}	Cointelegraph By Turner Wright	2021-03-09 18:15:00	\N
256	\N	Norweigan energy tycoon spins up new Bitcoin business 	Billionaire Kjell Inge Rokke believes BTC will become an important feature of the new monetary system. 	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvZmFiYWM4NGMtZDc5Zi00ZDgxLTk0MDctMjhjOTgxOWQ4M2Y1LmpwZw==.jpg	Billionaire Kjell Inge Rokke believes BTC will become an important feature of the new monetary system. 	{cryptocurrencies,adoption,"bitcoin price",energy}	Cointelegraph By Sam Bourgi	2021-03-09 19:30:00	\N
257	\N	Reserve Rights (RSR) gains 300% as stablecoins gain regulatory approval	Global stablecoin adoption and uncontrolled hyperinflation in Venezuela and Argentina have helped push Reserve Rights to a new all-time high.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvZDU1ZGU5ZTgtODIzMi00NjcwLTkyNTgtZGRmYjViMTE3MDY5LmpwZw==.jpg	Global stablecoin adoption and uncontrolled hyperinflation in Venezuela and Argentina have helped push Reserve Rights to a new all-time high.	{stablecoin,venezuela,hyperinflation,cryptocurrencies,markets,"cryptocurrency exchange","central bank","altcoin watch"}	Cointelegraph By Jordan Finneseth	2021-03-09 20:04:36	\N
258	\N	Gemini crypto exchange sponsoring the Oxford-Cambridge boat race 	"We are thrilled to be combining two of our greatest passions — rowing and crypto — with our sponsorship of this historic event," said the Winklevoss twins.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvYzM4MDM5ZmItODhhNS00ZDc2LWJmZDQtZTBiN2UyZGIwMzgxLmpwZw==.jpg	"We are thrilled to be combining two of our greatest passions — rowing and crypto — with our sponsorship of this historic event," said the Winklevoss twins.	{business,sport,"winkelvoss twins",gemini,"cryptocurrency exchange"}	Cointelegraph By Turner Wright	2021-03-09 20:10:00	\N
259	\N	'Ecological nightmare' backlash forces ArtStation to drop NFT plans	The announcement ArtStation was launching NFT artworks did not sit well with the platform’s artists, who called for a boycott and threatened to leave the platform.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvYWQ2ZTAxOWItNWRlYS00MjFhLTkxZmItZjA4N2IzZDM5YTdhLmpwZw==.jpg	The announcement ArtStation was launching NFT artworks did not sit well with the platform’s artists, who called for a boycott and threatened to leave the platform.	{news,artstation,nft,"non-fungible token","carbon footprint",criticism,"dapper labs",artist,art}	Cointelegraph By Joshua Mapperson	2021-03-10 01:38:48	\N
260	\N	‘Existential threat’ to Bitcoin-investing companies from carbon fallout	Asset managers are warning industry giants like Tesla and PayPal that investing in energy-intensive assets like Bitcoin could diminish their popularity among investors.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvMjdlYjI5NjctYjY3MS00ODEyLTgzOGYtOTk0MTBkZDRhOWVjLmpwZw==.jpg	Asset managers are warning industry giants like Tesla and PayPal that investing in energy-intensive assets like Bitcoin could diminish their popularity among investors.	{"bitcoin mining",electricity,"green technology",tesla,paypal,square}	Cointelegraph By Cyrus McNally	2021-03-10 01:41:07	\N
261	\N	Solution to scale Ethereum '100X’ is imminent and will get us through until Eth2: Vitalik 	The author of the Ethereum whitepaper, Vitalik Buterin, believes rollups will solve Ethereum’s scaling woes until the introduction of Eth2 sharding. 	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvMWY5MzFiNTEtNjRiYy00YmEyLWE1NmYtMTY3NWI3ZmMxYjZmLmpwZw==.jpg	The author of the Ethereum whitepaper, Vitalik Buterin, believes rollups will solve Ethereum’s scaling woes until the introduction of Eth2 sharding. 	{rollups,optimism,sharding}	Cointelegraph By Samuel Haig	2021-03-10 01:46:51	\N
262	\N	‘Game changer’ for Maker and Ethereum with Optimism Dai bridge announced	The Optimism-Dai Bridge will allow fast withdrawals later this year. 	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvNmZiMTg3NTMtYWNhYS00YzRkLWFmNGQtZGQ4NTlhYmVkNDJlLmpwZw==.jpg	The Optimism-Dai Bridge will allow fast withdrawals later this year. 	{makerdao,dai,optimism,rollups,"layer 2"}	Cointelegraph By Martin Young	2021-03-10 02:59:56	\N
263	\N	BlockFi users targeted in ‘racist and vulgar’ email attack	Roughly 500 people were in for a random surprise on Mar. 8, receiving a profanity-laced correspondence from BlockFi after their email addresses were used to sign up for fake accounts.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvYzE4ZDg5NWMtMDU2Mi00ZjZkLTg5NDgtOWQ5YTdjZjZhYzRlLmpwZw==.jpg	Roughly 500 people were in for a random surprise on Mar. 8, receiving a profanity-laced correspondence from BlockFi after their email addresses were used to sign up for fake accounts.	{spam,defi,"cryptocurrency exchange",lending}	Cointelegraph By Cyrus McNally	2021-03-10 04:13:57	\N
264	\N	’Breaking new ground is never easy’ — Kings of Leon's NFT release takes in $2M	Kings of Leon generated $1.45 million to $2 million in sales from their new NFT releases and will donate some of the proceeds to Live Nation’s Crew Nation Fund.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvYTRhZjhkNGEtODlkMy00ZDEwLThiM2EtMDNkZjdiNzM4Nzc1LmpwZw==.jpg	Kings of Leon generated $1.45 million to $2 million in sales from their new NFT releases and will donate some of the proceeds to Live Nation’s Crew Nation Fund.	{"kings of leon",nft,"new album",auction}	Cointelegraph By Brian Quarmby	2021-03-10 04:28:03	\N
265	\N	Case examiner says Cred platform hired escaped prisoner as CFO	Cred’s former Chief Financial Officer, James Alexander, has been identified by U.K. authorities as a prison escapee who was convicted for financial crimes.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvOGMzNjY4MGQtMDdmMS00YmYzLTlkOTAtNDY0ODcwZDQ5ZDI2LmpwZw==.jpg	Cred’s former Chief Financial Officer, James Alexander, has been identified by U.K. authorities as a prison escapee who was convicted for financial crimes.	{cred,"cred capital","james alexander"}	Cointelegraph By Samuel Haig	2021-03-10 04:41:00	\N
266	\N	Crypto influencers scramble to recover Twitter accounts after suspensions	Twitter suspended a number of major cryptocurrency-related accounts, including some of the industry’s biggest influencers.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvZjEzNDU5NjktNjgyZi00MmRkLTliNmItMGQwNDQ4MmM5YmM0LmpwZw==.jpg	Twitter suspended a number of major cryptocurrency-related accounts, including some of the industry’s biggest influencers.	{cryptocurrencies,twitter,"social media","jack dorsey",community}	Cointelegraph By Helen Partz	2021-03-10 07:59:26	\N
267	\N	Ripple ends YouTube lawsuit over XRP giveaway scams, says CEO 	Ripple and its CEO Brad Garlinghouse had alleged that YouTube profited monetarily from fraudulent XRP giveaways.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvODY1ZDhjYzgtMWZhOS00OGY1LThiOWItYjI3OGE1YWU1YjI5LmpwZw==.jpg	Ripple and its CEO Brad Garlinghouse had alleged that YouTube profited monetarily from fraudulent XRP giveaways.	{xrp,youtube}	Cointelegraph By Greg Thomson	2021-03-10 09:11:51	\N
268	\N	Korean crypto exchanges could soon face fines for gaps in due diligence measures	South Korean financial regulators are developing a penalty regime for Anti-Money Laundering and Know Your Customer violations by cryptocurrency exchanges. 	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvYjZlYjVjMDEtODI1MC00ODdhLThjNmMtMzdjNDBlMmY0OWU3LmpwZw==.jpg	South Korean financial regulators are developing a penalty regime for Anti-Money Laundering and Know Your Customer violations by cryptocurrency exchanges. 	{cryptocurrencies,"cryptocurrency exchange","south korea",government,fines,bithumb,aml,kyc}	Cointelegraph By Helen Partz	2021-03-10 10:24:33	\N
269	\N	Nvidia RTX 3060 mines Ether at full power as miners allegedly bypass hash limits	Nvidia's plans to separate gaming and mining demand could be foiled after a custom modification allegedly bypassed the RTX 3060's Ether mining limitations.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvMmU1MmRkOGMtMGJjZS00YWRlLTgyN2EtYmYzMjc3MTg2ZjM1LmpwZw==.jpg	Nvidia's plans to separate gaming and mining demand could be foiled after a custom modification allegedly bypassed the RTX 3060's Ether mining limitations.	{nvidia,mining}	Cointelegraph By Greg Thomson	2021-03-10 10:41:30	\N
270	\N	The number of Bitcoin ATMs in the US rose 177% over the past year	Worldwide, close to 10,000 new Bitcoin ATMs have been installed since March 1, 2020.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvNTJkN2IxOTgtMDI3ZC00NjUxLWFkMzUtYjJjYWI1NmY4ZDRlLmpwZw==.jpg	Worldwide, close to 10,000 new Bitcoin ATMs have been installed since March 1, 2020.	{cryptocurrencies,news,"united states","united kingdom",europe,canada,colombia,"hong kong",russia,atm,coinatmradar}	Cointelegraph By Marie Huillet 	2021-03-10 10:47:35	\N
271	\N	South Korean Shinhan Bank pilots digital currency platform with LG CNS	South Korean commercial bank Shinhan Bank has built a digital won pilot platform in collaboration with LG CNS.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvMjk3OGU1NzUtZjAwZi00MWM0LWJhZGYtYWZjOTIzNGY1Yjk5LmpwZw==.jpg	South Korean commercial bank Shinhan Bank has built a digital won pilot platform in collaboration with LG CNS.	{cbdc,"central bank",banks,"digital currency","south korea"}	Cointelegraph By Helen Partz	2021-03-10 11:57:15	\N
272	\N	Bitcoin price focuses on $55K as bulls ignore a surging US dollar	The classic inverse correlation between the U.S. dollar and Bitcoin is nowhere to be seen as all-time highs come closer and closer.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvN2QwMWVkMmMtYmUyYi00MWVkLWJmZjItZDI0OWRjYzczM2YyLmpwZw==.jpg	The classic inverse correlation between the U.S. dollar and Bitcoin is nowhere to be seen as all-time highs come closer and closer.	{bitcoin,"bitcoin price",dxy,usd,dollar}	Cointelegraph By William Suberg	2021-03-10 12:40:09	\N
273	\N	Grayscale parent Digital Currency Group plans to buy $250M of its own GBTC shares	In a further sign of a shake-up in the institutional market, DCG is eyeing the purchase along with hiring ETF specialists.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvZmM5OTVhMGUtMWYxMC00NzdmLWE2MWMtMGQxYzFlNTg4MzE0LmpwZw==.jpg	In a further sign of a shake-up in the institutional market, DCG is eyeing the purchase along with hiring ETF specialists.	{bitcoin,etf,grayscale,gbtc}	Cointelegraph By William Suberg	2021-03-10 13:33:06	\N
274	\N	Ethereum vs. Bitcoin: Did ETH/BTC just bottom as Ether eyes $2K? 	The ETH/BTC pair is showing signs of bottoming, but further consolidation should not be ruled out. 	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvOTZlNzRlYTEtODZhOS00YWFmLWIzZjUtYTI1NDc3MWJmMjIxLmpwZw==.jpg	The ETH/BTC pair is showing signs of bottoming, but further consolidation should not be ruled out. 	{"ethereum price","eth price",ether,"technical analysis","price analysis"}	Cointelegraph By Michaël van de Poppe	2021-03-10 14:01:46	\N
275	\N	Much fun, no work: DOGE must ditch meme status to be valued as money	Doge reaching $1 is practically impossible unless the currency witnesses some Bitcoin-level buy-ins in the coming future.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvOTcxMTUyMGItYzVhNC00YTFhLWFjMGItZTcwYTI3NzIwZWI4LmpwZw==.jpg	Doge reaching $1 is practically impossible unless the currency witnesses some Bitcoin-level buy-ins in the coming future.	{dogecoin,adoption}	Cointelegraph By Shiraz Jagati	2021-03-10 15:04:01	\N
276	\N	The things the DoJ’s latest move against McAfee has taught us	The actions against McAfee by the DoJ and the IRS represent a worrying shift in the view of the U.S. government’s criminal enforcement arm.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvYmZlZjcxZGItMmMzMi00YTM3LTg2MTUtODI0YTEyMmRlNTk0LmpwZw==.jpg	The actions against McAfee by the DoJ and the IRS represent a worrying shift in the view of the U.S. government’s criminal enforcement arm.	{cryptocurrencies,"united states",government,"john mcafee",mcafee,"department of justice",irs,sec}	Cointelegraph By Cal Evans	2021-03-10 16:07:25	\N
277	\N	Blockchain group INATBA reiterates concerns over proposed European regulations	The International Association for Trusted Blockchain Applications has faulted some aspects of the European Commission’s proposed crypto regulations.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvMmU3NjFiOTMtZTJjMS00YzU2LWE5NTItYTk1ZTU0ZDJlZWY5LmpwZw==.jpg	The International Association for Trusted Blockchain Applications has faulted some aspects of the European Commission’s proposed crypto regulations.	{inatba,blockchain,crypto}	Cointelegraph By Osato Avan-Nomayo	2021-03-10 17:15:00	\N
278	\N	Grayscale halts GBTC inflow after record 15% discount	Just a few days after GBTC traded at a 15% discount to its BTC equivalent, Grayscale Investments has temporarily closed the trust to investors.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvMzhhN2QyZDctMTA0ZC00ZGZlLWFmNzAtYjA5Zjc1ODVhNjA1LmpwZw==.jpg	Just a few days after GBTC traded at a 15% discount to its BTC equivalent, Grayscale Investments has temporarily closed the trust to investors.	{cryptocurrencies,"bitcoin price",markets,grayscale,"market analysis"}	Cointelegraph By Marcel Pechman	2021-03-10 17:34:58	\N
279	\N	Good correction? Bitcoin price regains $57K as institutions buy the dip	Bitcoin’s price bounces back to the $50,000 range as new investors show weak hands during the dip.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvYTYwMzA4MDgtODNjMC00N2Y5LTgwZTEtMzE2YjVhNGNkOWY0LmpwZw==.jpg	Bitcoin’s price bounces back to the $50,000 range as new investors show weak hands during the dip.	{}	Cointelegraph By Anirudh Tiwari	2021-03-10 17:57:14	\N
280	\N	Dash rolls out Ethereum DeFi bridge with staking and yield farming	Dash is set to release its Ethereum decentralized finance bridge after months of testing.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvOTYxZGU1NjAtMTk2Ny00NTRkLWIxM2UtMWI0M2IxM2Q4NTMwLmpwZw==.jpg	Dash is set to release its Ethereum decentralized finance bridge after months of testing.	{dash,ethereum,defi}	Cointelegraph By Osato Avan-Nomayo	2021-03-10 18:00:00	\N
281	\N	New Zealand firm launches stablecoin backed by NZD	"Techemynt felt it was an ideal time to fill the gap in the market and lead the creation of a NZD-based stablecoin," said Fran Strajnar.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvMzM3OGRiNzMtMTliMy00ZWM2LWFjNmEtMWUzYmQ4NmNiY2YyLmpwZw==.jpg	"Techemynt felt it was an ideal time to fill the gap in the market and lead the creation of a NZD-based stablecoin," said Fran Strajnar.	{business,"new zealand",stablecoin,techemynt}	Cointelegraph By Turner Wright	2021-03-10 18:15:00	\N
282	\N	Bitcoin bulls stampede toward $60,000 after a key BTC resistance is broken	Bitcoin price takes aim at a new all-time high as traders push BTC back above $57,000 and institutions increase their exposure to cryptocurrency.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvNDg4YzhkZTgtYzQwMy00NDJiLTg1MDItOTZmNWUwOTM0MGUzLmpwZw==.jpg	Bitcoin price takes aim at a new all-time high as traders push BTC back above $57,000 and institutions increase their exposure to cryptocurrency.	{"bitcoin price",markets,"market update","ether price"}	Cointelegraph By Jordan Finneseth	2021-03-10 18:34:18	\N
283	\N	3 key Bitcoin price metrics signal this ‘healthy’ rally has room to run	A stable funding rate paired with a record-high $20.3 billion open interest on BTC futures suggest that the current rally has room to extend higher.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvMmI1MzhhNDQtZTc3NC00YjUxLWE1NWUtZTgxYTgwMmU2ZTg4LmpwZw==.jpg	A stable funding rate paired with a record-high $20.3 billion open interest on BTC futures suggest that the current rally has room to extend higher.	{markets,"market analysis","bitcoin price",futures,derivatives,cryptocurrencies,"cryptocurrency exchange"}	Cointelegraph By Marcel Pechman	2021-03-10 20:28:44	\N
284	\N	How crypto donations are helping victims of the Texas winter storm 	"We intend to accept donations via cryptocurrency essentially forever," said the Austin Disaster Relief Network's chief financial officer.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvZDBiMTYwODYtMzJhYy00YzkxLWFlZTktMGYzNGZmNDFmODg2LmpwZw==.jpg	"We intend to accept donations via cryptocurrency essentially forever," said the Austin Disaster Relief Network's chief financial officer.	{business,texas,weather,donations,charity,"crypto winter"}	Cointelegraph By Turner Wright	2021-03-10 20:37:16	\N
285	\N	Bitcoin demand from Goldman Sachs clients 'is rising,' says COO	John Waldron said the investment management firm would “continue to evaluate” and “engage on” crypto for customers.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvZjhjM2U4ZGItY2Y4YS00NjE0LThlOTQtODY0ODA3ODQ2Yjk2LmpwZw==.jpg	John Waldron said the investment management firm would “continue to evaluate” and “engage on” crypto for customers.	{business,"goldman sachs","bitcoin adoption","wall street"}	Cointelegraph By Turner Wright	2021-03-10 21:10:43	\N
286	\N	Price analysis 3/10: BTC, ETH, BNB, ADA, DOT, XRP, UNI, LTC, LINK, BCH	Select altcoins could rally to new all-time highs if Bitcoin bulls successfully hold BTC above the $57,000 level. 	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvYzNlOWJjY2ItYmQ5Zi00OTA0LWFmOGQtMmJjY2ViMzBlMTkxLmpwZw==.jpg	Select altcoins could rally to new all-time highs if Bitcoin bulls successfully hold BTC above the $57,000 level. 	{markets,bitcoin,ethereum,"binance coin",cardano,polkadot,ripple,uniswap,litecoin,chainlink,"bitcoin cash","price analysis"}	Cointelegraph By Rakesh Upadhyay	2021-03-10 22:05:00	\N
287	\N	Not just another gas token — Theta’s TFUEL surges 775% in 5 weeks 	Theta Fuel price has been on a triple-digit rally since the beginning of February, a signal that the gas token will play an integral role in the rapidly expanding Theta ecosystem.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvNzYwMzJiMjktYTM3Zi00OWNiLWEwNjUtZDYzZThiYmJmNTNmLmpwZw==.jpg	Theta Fuel price has been on a triple-digit rally since the beginning of February, a signal that the gas token will play an integral role in the rapidly expanding Theta ecosystem.	{proof-of-stake,cryptocurrencies,markets,"altcoin watch"}	Cointelegraph By Jordan Finneseth	2021-03-11 00:00:00	\N
288	\N	Israeli asset manager doubles its $100M Bitcoin investment in just two months 	The firm invested $100M into the Grayscale Bitcoin Trust late last year, it has sold one third and it's holdings are still worth $50M more than the purchase price.	https://images.cointelegraph.com/images/840_aHR0cHM6Ly9zMy5jb2ludGVsZWdyYXBoLmNvbS91cGxvYWRzLzIwMjEtMDMvZDJiYmU4OWUtMzQ3Ny00MDJkLWIyNjktYjhmNmFmZjhhNmJkLmpwZw==.jpg	The firm invested $100M into the Grayscale Bitcoin Trust late last year, it has sold one third and it's holdings are still worth $50M more than the purchase price.	{cryptocurrencies,investments}	Cointelegraph By Andrew Fenton	2021-03-11 00:22:17	\N
\.


--
-- Data for Name: publications; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.publications (id, created_by, content, tickers, created, updated) FROM stdin;
1	3	Can't wait for $BNB and $XEM are 1000$ is amazing	{"bnb,xem"}	2021-02-10 10:17:18.627	\N
2	3	I bet $BTC's price will be around $100k in a few next years	{btc}	2021-02-10 10:17:51.211	\N
3	3	How about $ETH price?	{eth}	2021-02-10 10:18:05.787	\N
4	3	Наконец-то я могу сказать, что $TUSD сделал мой день	{tusd}	2021-02-10 10:18:21.45	\N
5	3	Взял $QTUM на 15% от депозита, жду иксов :)))	{qtum}	2021-02-10 10:18:45.622	\N
6	3	$XRP will overcome $BNB in this month!!	{"bnb,xrp"}	2021-02-10 10:19:05.751	\N
7	3	$USD is better than $EUR	{"eur,usd"}	2021-02-10 10:19:31.149	\N
8	3	Мне кажется, доллар сейчас в просадке	{}	2021-02-10 10:19:40.791	\N
9	4	Перепутал доллар $USD с $TUSD	{tusd}	2021-02-10 10:20:06.604	\N
10	4	Ну когда уже разбогатеем?? $XEM	{xem}	2021-02-10 10:20:49.972	\N
11	4	Надо было брать $XEM $2 года назад	{xem}	2021-02-10 10:21:04.609	\N
12	4	Я не покупал $BTC, потому что не было денег	{btc}	2021-02-10 10:21:17.019	\N
13	4	$CAKE I really like DeFi projects btw	{cake}	2021-02-10 10:21:44.115	\N
14	4	Что думаете по $XRP рипплу? Улетит?	{xrp}	2021-02-10 10:22:01.258	\N
15	5	Скорее бы уже начать делать фронтенд :)) $INNO	{inno}	2021-02-10 10:22:44.956	\N
16	5	Не могу дождаться, когда начну делать фронтенд $BTC	{btc}	2021-02-10 10:22:58.697	\N
17	5	Вау вот бы $ETH фронтенд заработал и мы разбогатели	{eth}	2021-02-10 10:23:14.511	\N
18	5	В след году куплю акции $CDPR, после того, как хакеры пропатчат киберпанк, и акции взлетят	{cdpr}	2021-02-10 10:23:46.689	\N
19	5	Gonna go to shop and buy that chocko podusechki for 2 $XEM	{xem}	2021-02-10 10:24:35.088	\N
\.


--
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.roles (id, name) FROM stdin;
1	ROLE_USER
2	ROLE_MODERATOR
3	ROLE_ADMIN
\.


--
-- Data for Name: user_reading_list; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_reading_list (user_id, news_id) FROM stdin;
\.


--
-- Data for Name: user_roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_roles (user_id, role_id) FROM stdin;
1	1
2	1
2	2
2	3
3	1
4	1
5	1
6	1
6	2
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, username, email, password, is_enabled, is_account_non_expired, is_account_non_locked, is_credentials_non_expired) FROM stdin;
1	test	test@test	$2a$10$77JkY0wPJhgmY/keWnUKq.utL/q/fQhvSW1dL3sFnxvdApuTH/R9q	t	t	t	t
2	admin	admin@admin	$2a$10$3Bii/M.wU1V.nbPq6gm0J.moTmCrGJ9VOmk5zElK.3ugJ3vJINK7K	t	t	t	t
3	artur	artur@artur	$2a$10$77JkY0wPJhgmY/keWnUKq.utL/q/fQhvSW1dL3sFnxvdApuTH/R9q	t	t	t	t
4	ibaction	ibaction@ibaction	$2a$10$77JkY0wPJhgmY/keWnUKq.utL/q/fQhvSW1dL3sFnxvdApuTH/R9q	t	t	t	t
5	albert	albert@albert	$2a$10$77JkY0wPJhgmY/keWnUKq.utL/q/fQhvSW1dL3sFnxvdApuTH/R9q	t	t	t	t
6	moderator	moderator@moderator	$2a$10$77JkY0wPJhgmY/keWnUKq.utL/q/fQhvSW1dL3sFnxvdApuTH/R9q	t	t	t	t
\.


--
-- Name: comments_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.comments_id_seq', 31, true);


--
-- Name: comments_post_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.comments_post_id_seq', 1, false);


--
-- Name: complaints_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.complaints_id_seq', 1, false);


--
-- Name: complaints_post_author_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.complaints_post_author_seq', 1, false);


--
-- Name: complaints_post_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.complaints_post_id_seq', 1, false);


--
-- Name: confirmation_tokens_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.confirmation_tokens_id_seq', 1, false);


--
-- Name: currencies_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.currencies_id_seq', 1000, true);


--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.hibernate_sequence', 1, false);


--
-- Name: likes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.likes_id_seq', 60, true);


--
-- Name: likes_post_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.likes_post_id_seq', 1, false);


--
-- Name: likes_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.likes_user_id_seq', 1, false);


--
-- Name: news_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.news_id_seq', 288, true);


--
-- Name: publications_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.publications_id_seq', 19, true);


--
-- Name: roles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.roles_id_seq', 3, true);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 6, true);


--
-- Name: comments comments_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comments
    ADD CONSTRAINT comments_pk PRIMARY KEY (id);


--
-- Name: complaints complaints_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.complaints
    ADD CONSTRAINT complaints_pk PRIMARY KEY (id);


--
-- Name: confirmation_tokens confirmation_tokens_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.confirmation_tokens
    ADD CONSTRAINT confirmation_tokens_pk PRIMARY KEY (id);


--
-- Name: currencies currencies_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.currencies
    ADD CONSTRAINT currencies_pk PRIMARY KEY (id);


--
-- Name: flyway_schema_history flyway_schema_history_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.flyway_schema_history
    ADD CONSTRAINT flyway_schema_history_pk PRIMARY KEY (installed_rank);


--
-- Name: likes likes_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.likes
    ADD CONSTRAINT likes_pk PRIMARY KEY (id);


--
-- Name: news news_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.news
    ADD CONSTRAINT news_pk PRIMARY KEY (id);


--
-- Name: publications publications_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.publications
    ADD CONSTRAINT publications_pk PRIMARY KEY (id);


--
-- Name: roles roles_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pk PRIMARY KEY (id);


--
-- Name: user_reading_list user_reading_list_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_reading_list
    ADD CONSTRAINT user_reading_list_pk PRIMARY KEY (user_id, news_id);


--
-- Name: user_roles user_roles_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT user_roles_pk PRIMARY KEY (user_id, role_id);


--
-- Name: users users_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pk PRIMARY KEY (id);


--
-- Name: flyway_schema_history_s_idx; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX flyway_schema_history_s_idx ON public.flyway_schema_history USING btree (success);


--
-- Name: idx_currencies_ticker; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_currencies_ticker ON public.currencies USING btree (ticker);


--
-- Name: likes_post_type_post_id_user_id_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX likes_post_type_post_id_user_id_uindex ON public.likes USING btree (post_type, post_id, user_id);


--
-- Name: users_email_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX users_email_uindex ON public.users USING btree (email);


--
-- Name: users_username_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX users_username_uindex ON public.users USING btree (username);


--
-- PostgreSQL database dump complete
--

--
-- Database "postgres" dump
--

--
-- PostgreSQL database dump
--

-- Dumped from database version 13.1 (Debian 13.1-1.pgdg100+1)
-- Dumped by pg_dump version 13.1 (Debian 13.1-1.pgdg100+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE postgres;
--
-- Name: postgres; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE postgres WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US.utf8';


ALTER DATABASE postgres OWNER TO postgres;

\connect postgres

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: DATABASE postgres; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON DATABASE postgres IS 'default administrative connection database';


--
-- PostgreSQL database dump complete
--

--
-- PostgreSQL database cluster dump complete
--

