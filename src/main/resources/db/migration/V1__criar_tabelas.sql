CREATE TABLE public.profissionais (
    id UUID PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    cargo VARCHAR(13),
    nascimento DATE,
    created_date DATE
);

CREATE TABLE public.contatos (
    id UUID PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    contato VARCHAR(255),
    created_date DATE,
    id_profissional UUID NOT NULL,
    CONSTRAINT fk_contatos_profissional
        FOREIGN KEY (id_profissional)
        REFERENCES profissionais(id)
        ON DELETE CASCADE
);

