ALTER TABLE IF EXISTS public.profissionais
    ADD COLUMN inativo boolean NOT NULL DEFAULT false;