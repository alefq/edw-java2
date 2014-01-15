--
-- PostgreSQL database dump
--

-- Dumped from database version 9.1.11
-- Dumped by pg_dump version 9.1.11
-- Started on 2014-01-15 18:53:28 PYST

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 165 (class 3079 OID 11679)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 1907 (class 0 OID 0)
-- Dependencies: 165
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_with_oids = false;

--
-- TOC entry 161 (class 1259 OID 38892)
-- Dependencies: 1792 6
-- Name: chat_audit; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE chat_audit (
    chat_audit_id bigint NOT NULL,
    nickname character varying(50),
    numero_ip character varying(20),
    texto character varying(1000),
    fecha timestamp without time zone DEFAULT now() NOT NULL
);


--
-- TOC entry 162 (class 1259 OID 38899)
-- Dependencies: 6 161
-- Name: chat_audit_chat_audit_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE chat_audit_chat_audit_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 1908 (class 0 OID 0)
-- Dependencies: 162
-- Name: chat_audit_chat_audit_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE chat_audit_chat_audit_id_seq OWNED BY chat_audit.chat_audit_id;


--
-- TOC entry 163 (class 1259 OID 38901)
-- Dependencies: 1794 6
-- Name: conexion_audit; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE conexion_audit (
    conexion_audit_id bigint NOT NULL,
    nickname character varying(50),
    tipo character varying(10),
    fecha timestamp without time zone DEFAULT now() NOT NULL
);


--
-- TOC entry 164 (class 1259 OID 38905)
-- Dependencies: 163 6
-- Name: conexion_audit_chat_audit_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE conexion_audit_chat_audit_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 1909 (class 0 OID 0)
-- Dependencies: 164
-- Name: conexion_audit_chat_audit_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE conexion_audit_chat_audit_id_seq OWNED BY conexion_audit.conexion_audit_id;


--
-- TOC entry 1793 (class 2604 OID 38907)
-- Dependencies: 162 161
-- Name: chat_audit_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY chat_audit ALTER COLUMN chat_audit_id SET DEFAULT nextval('chat_audit_chat_audit_id_seq'::regclass);


--
-- TOC entry 1795 (class 2604 OID 38908)
-- Dependencies: 164 163
-- Name: conexion_audit_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY conexion_audit ALTER COLUMN conexion_audit_id SET DEFAULT nextval('conexion_audit_chat_audit_id_seq'::regclass);


--
-- TOC entry 1797 (class 2606 OID 38910)
-- Dependencies: 161 161 1902
-- Name: pk_chat_audit_table; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY chat_audit
    ADD CONSTRAINT pk_chat_audit_table PRIMARY KEY (chat_audit_id);


--
-- TOC entry 1799 (class 2606 OID 38912)
-- Dependencies: 163 163 1902
-- Name: pk_conexion_audit_id; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY conexion_audit
    ADD CONSTRAINT pk_conexion_audit_id PRIMARY KEY (conexion_audit_id);


-- Completed on 2014-01-15 18:53:28 PYST

--
-- PostgreSQL database dump complete
--

