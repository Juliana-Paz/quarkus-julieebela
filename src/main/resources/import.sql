-- ============================================================
-- Julie e Bela — Seed de desenvolvimento
-- Ordem: respeita FKs (pai antes de filho)
-- ============================================================

-- ============================================================
-- 1. ESTADOS (domínio do professor — mantidos)
-- ============================================================
INSERT INTO estado (nome, sigla, regiao) VALUES ('Tocantins',          'TO', 2);
INSERT INTO estado (nome, sigla, regiao) VALUES ('São Paulo',          'SP', 4);
INSERT INTO estado (nome, sigla, regiao) VALUES ('Goiás',              'GO', 1);
INSERT INTO estado (nome, sigla, regiao) VALUES ('Rio Grande do Sul',  'RS', 5);
INSERT INTO estado (nome, sigla, regiao) VALUES ('Rio de Janeiro',     'RJ', 4);

-- ============================================================
-- 2. MUNICÍPIOS (domínio do professor — mantidos)
-- ============================================================
INSERT INTO municipio (nome, id_estado) VALUES ('Palmas',         1);
INSERT INTO municipio (nome, id_estado) VALUES ('Gurupi',         1);
INSERT INTO municipio (nome, id_estado) VALUES ('São Paulo',      2);
INSERT INTO municipio (nome, id_estado) VALUES ('Rio de Janeiro', 5);

-- ============================================================
-- 3. USUÁRIOS
-- admin    senha: admin123
-- cliente1 senha: 123456
-- Perfil: ADM=1 | USER=2
-- ============================================================
INSERT INTO usuario (nome, username, senha, perfil)
VALUES ('Administrador', 'admin',
        'SYu34Plo5KZGE9fMtUK9LRPnWC3WvVpogVg35bf5tPYMM6dxXNV6AWmPEQzOLc110uIwcv8TOigbaCB43f8KHQ==',
        1);

INSERT INTO usuario (nome, username, senha, perfil)
VALUES ('Maria Silva', 'cliente1',
        '0cctg7WgpEz7kC/AzVC+KX+bZLPXDtgJDqWWZWnmzHH+7Na2YVxYYSFPxcf7ImAjqfNckx0aT4n5qKM7WEoeEQ==',
        2);

-- ============================================================
-- 4. CATEGORIAS
-- ============================================================
INSERT INTO categoria (nome, descricao) VALUES ('Pijama Infantil Menina', 'Pijamas femininos de 0 a 12 anos');
INSERT INTO categoria (nome, descricao) VALUES ('Pijama Infantil Menino', 'Pijamas masculinos de 0 a 12 anos');
INSERT INTO categoria (nome, descricao) VALUES ('Pijama Unissex',         'Pijamas unissex para todas as idades');
INSERT INTO categoria (nome, descricao) VALUES ('Pijama Adulto',          'Pijamas adultos femininos e masculinos');

-- ============================================================
-- 5. MARCAS
-- ============================================================
INSERT INTO marca (nome, descricao) VALUES ('Julie',        'Linha feminina exclusiva da Julie e Bela');
INSERT INTO marca (nome, descricao) VALUES ('Bela',         'Linha masculina exclusiva da Julie e Bela');
INSERT INTO marca (nome, descricao) VALUES ('Julie e Bela', 'Linha unissex da Julie e Bela');

-- ============================================================
-- 6. ESTAMPAS
-- ============================================================
INSERT INTO estampa (nome) VALUES ('Unicórnios');
INSERT INTO estampa (nome) VALUES ('Dinossauros');
INSERT INTO estampa (nome) VALUES ('Estrelas');
INSERT INTO estampa (nome) VALUES ('Liso');

-- ============================================================
-- 7. CORES
-- ============================================================
INSERT INTO cor (nome, hexadecimal) VALUES ('Rosa',    '#FF69B4');
INSERT INTO cor (nome, hexadecimal) VALUES ('Azul',    '#4169E1');
INSERT INTO cor (nome, hexadecimal) VALUES ('Amarelo', '#FFD700');
INSERT INTO cor (nome, hexadecimal) VALUES ('Branco',  '#FFFFFF');
INSERT INTO cor (nome, hexadecimal) VALUES ('Roxo',    '#800080');

-- ============================================================
-- 8. MATERIAIS
-- ============================================================
INSERT INTO material (nome, descricao) VALUES ('Algodão', '100% algodão hipoalergênico — macio e respirável');
INSERT INTO material (nome, descricao) VALUES ('Malha',   'Malha de algodão com elastano — confortável e flexível');
INSERT INTO material (nome, descricao) VALUES ('Fleece',  'Tecido quentinho, ideal para noites frias');

-- ============================================================
-- 9. PIJAMAS
-- Sexo: FEMININO=1 | MASCULINO=2 | UNISSEX=3
-- ============================================================
INSERT INTO pijama (nome, descricao, preco, modelo, ativo, sexo, categoria_id, marca_id, estampa_id)
VALUES ('Pijama Unicórnio Rosa',
        'Pijama manga longa com estampa de unicórnios mágicos, em algodão premium',
        79.90, 'Manga Longa', true, 1, 1, 1, 1);

INSERT INTO pijama (nome, descricao, preco, modelo, ativo, sexo, categoria_id, marca_id, estampa_id)
VALUES ('Pijama Estrelas Lilás',
        'Pijama manga curta com estrelinhas brilhantes, perfeito para o verão',
        89.90, 'Manga Curta', true, 1, 1, 1, 3);

INSERT INTO pijama (nome, descricao, preco, modelo, ativo, sexo, categoria_id, marca_id, estampa_id)
VALUES ('Pijama Dinossauro',
        'Pijama manga longa temático de dinossauros, ideal para os pequenos aventureiros',
        79.90, 'Manga Longa', true, 2, 2, 2, 2);

INSERT INTO pijama (nome, descricao, preco, modelo, ativo, sexo, categoria_id, marca_id, estampa_id)
VALUES ('Pijama Liso Azul',
        'Pijama masculino liso em malha premium, clássico e confortável',
        69.90, 'Manga Curta', true, 2, 2, 2, 4);

INSERT INTO pijama (nome, descricao, preco, modelo, ativo, sexo, categoria_id, marca_id, estampa_id)
VALUES ('Pijama Unissex Estrelas',
        'Pijama unissex com estampa de estrelas, perfeito para presentear',
        84.90, 'Manga Longa', true, 3, 3, 3, 3);

INSERT INTO pijama (nome, descricao, preco, modelo, ativo, sexo, categoria_id, marca_id, estampa_id)
VALUES ('Pijama Fleece Lilás',
        'Pijama quentinho de fleece para noites frias, disponível em lilás e rosa',
        94.90, 'Manga Longa', true, 1, 1, 1, 4);

INSERT INTO pijama (nome, descricao, preco, modelo, ativo, sexo, categoria_id, marca_id, estampa_id)
VALUES ('Pijama Body Recém-Nascido',
        'Body macio em algodão premium para recém-nascidos, com estampa de unicórnios',
        59.90, 'Body', true, 3, 3, 3, 1);

-- ============================================================
-- 10. PIJAMA × MATERIAL
-- ============================================================
INSERT INTO pijama_material (pijama_id, material_id) VALUES (1, 1); -- Unicórnio Rosa   → Algodão
INSERT INTO pijama_material (pijama_id, material_id) VALUES (1, 2); -- Unicórnio Rosa   → Malha
INSERT INTO pijama_material (pijama_id, material_id) VALUES (2, 1); -- Estrelas Lilás   → Algodão
INSERT INTO pijama_material (pijama_id, material_id) VALUES (3, 1); -- Dinossauro       → Algodão
INSERT INTO pijama_material (pijama_id, material_id) VALUES (3, 2); -- Dinossauro       → Malha
INSERT INTO pijama_material (pijama_id, material_id) VALUES (4, 2); -- Liso Azul        → Malha
INSERT INTO pijama_material (pijama_id, material_id) VALUES (4, 3); -- Liso Azul        → Fleece
INSERT INTO pijama_material (pijama_id, material_id) VALUES (5, 1); -- Unissex Estrelas → Algodão
INSERT INTO pijama_material (pijama_id, material_id) VALUES (5, 2); -- Unissex Estrelas → Malha
INSERT INTO pijama_material (pijama_id, material_id) VALUES (6, 3); -- Fleece Lilás     → Fleece
INSERT INTO pijama_material (pijama_id, material_id) VALUES (7, 1); -- Body RN          → Algodão

-- ============================================================
-- 11. PIJAMA × VARIANTE
-- Pijamas 1, 2, 3, 6: COM cor | Pijamas 4, 5, 7: SEM cor
-- ============================================================
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (1, 'QUATRO_ANOS', 1, 10);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (1, 'SEIS_ANOS',   1,  8);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (1, 'QUATRO_ANOS', 5,  5);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (1, 'SEIS_ANOS',   5,  3);

INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (2, 'SEIS_ANOS',   1, 12);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (2, 'OITO_ANOS',   1,  8);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (2, 'SEIS_ANOS',   3,  6);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (2, 'OITO_ANOS',   3,  4);

INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (3, 'QUATRO_ANOS', 2, 10);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (3, 'SEIS_ANOS',   2,  8);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (3, 'OITO_ANOS',   2,  6);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (3, 'QUATRO_ANOS', 4,  5);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (3, 'SEIS_ANOS',   4,  3);

INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (4, 'SEIS_ANOS',   null, 15);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (4, 'OITO_ANOS',   null, 12);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (4, 'DEZ_ANOS',    null,  8);

INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (5, 'DOIS_ANOS',   null, 10);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (5, 'QUATRO_ANOS', null,  6);

INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (6, 'SEIS_ANOS',   1,  8);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (6, 'OITO_ANOS',   1,  6);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (6, 'SEIS_ANOS',   5,  5);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (6, 'OITO_ANOS',   5,  4);

INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (7, 'RN',                null, 15);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (7, 'P',   null, 12);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (7, 'M', null,  8);

-- ============================================================
-- 12. CLIENTE  (vinculado ao usuario id=2 / cliente1)
-- ============================================================
INSERT INTO cliente (nome, cpf, email, data_nascimento, usuario_id)
VALUES ('Maria Silva', '12345678901', 'maria@email.com', '1995-06-15', 2);

-- ============================================================
-- 13. TELEFONE DO CLIENTE
-- ============================================================
INSERT INTO cliente_telefone (cliente_id, codigo_area, numero)
VALUES (1, '63', '999990001');

-- ============================================================
-- 14. ENDEREÇO DO CLIENTE
-- ============================================================
INSERT INTO cliente_endereco (cliente_id, logradouro, numero, complemento, bairro, cep, municipio, estado, principal)
VALUES (1, 'Quadra 203 Sul', '12', 'Apto 301', 'Plano Diretor Sul', '77020-016', 'Palmas', 'Tocantins', true);

-- ============================================================
-- 15. CUPONS DE DESCONTO
-- percentual=true  → valor_desconto é % (ex: 10.0 = 10%)
-- percentual=false → valor_desconto é R$ fixo
-- ============================================================
INSERT INTO CupomDesconto (codigo, descricao, valor_desconto, percentual, data_inicio, data_fim, ativo)
VALUES ('JULIE10', '10% de desconto em qualquer pijama Julie e Bela',
        10.0, true, '2026-01-01', '2026-12-31', true);

INSERT INTO CupomDesconto (codigo, descricao, valor_desconto, percentual, data_inicio, data_fim, ativo)
VALUES ('FRETE20', 'R$ 20,00 de desconto — válido em compras acima de R$ 150,00',
        20.0, false, '2026-01-01', '2026-12-31', true);

-- ============================================================
-- 16. PIJAMAS ADICIONAIS (IDs 8–27)
-- Cobrem: todas as categorias × todos os modelos × materiais ×
--         tamanhos variados × cores × preços × ativo/inativo
-- Sexo: FEMININO=1 | MASCULINO=2 | UNISSEX=3
-- ============================================================

-- ── MENINA (categoria_id=1, marca_id=1 Julie) ──────────────

-- 8 Body para bebê menina
INSERT INTO pijama (nome, descricao, preco, modelo, ativo, sexo, categoria_id, marca_id, estampa_id)
VALUES ('Body Liso Rosa', 'Body em algodão premium para bebê menina, macio e hipoalergênico',
        49.90, 'Body', true, 1, 1, 1, 4);

-- 9 Macacão Unicórnio menina
INSERT INTO pijama (nome, descricao, preco, modelo, ativo, sexo, categoria_id, marca_id, estampa_id)
VALUES ('Macacão Unicórnio', 'Macacão manga longa com estampa de unicórnio — quentinho e fofo',
        99.90, 'Macacão', true, 1, 1, 1, 1);

-- 10 Manga Curta Estrelas menina
INSERT INTO pijama (nome, descricao, preco, modelo, ativo, sexo, categoria_id, marca_id, estampa_id)
VALUES ('Pijama Estrelas Pink', 'Manga curta com estampas de estrelas brilhantes em tons de rosa',
        69.90, 'Manga Curta', true, 1, 1, 1, 3);

-- 11 Manga Longa Fleece menina
INSERT INTO pijama (nome, descricao, preco, modelo, ativo, sexo, categoria_id, marca_id, estampa_id)
VALUES ('Pijama Fleece Roxo Menina', 'Manga longa em fleece quentinho para noites frias, cor lilás',
        109.90, 'Manga Longa', true, 1, 1, 1, 4);

-- ── MENINO (categoria_id=2, marca_id=2 Bela) ───────────────

-- 12 Body para bebê menino
INSERT INTO pijama (nome, descricao, preco, modelo, ativo, sexo, categoria_id, marca_id, estampa_id)
VALUES ('Body Liso Azul', 'Body em algodão para bebê menino, toque suave e hipoalergênico',
        49.90, 'Body', true, 2, 2, 2, 4);

-- 13 Macacão Dinossauro menino
INSERT INTO pijama (nome, descricao, preco, modelo, ativo, sexo, categoria_id, marca_id, estampa_id)
VALUES ('Macacão Dino Aventura', 'Macacão divertido com estampas de dinossauros — aventura garantida',
        89.90, 'Macacão', true, 2, 2, 2, 2);

-- 14 Manga Longa Liso menino
INSERT INTO pijama (nome, descricao, preco, modelo, ativo, sexo, categoria_id, marca_id, estampa_id)
VALUES ('Pijama Clássico Azul', 'Manga longa liso em malha premium — confortável e durável',
        74.90, 'Manga Longa', true, 2, 2, 2, 4);

-- 15 Manga Curta Liso menino
INSERT INTO pijama (nome, descricao, preco, modelo, ativo, sexo, categoria_id, marca_id, estampa_id)
VALUES ('Pijama Verão Menino', 'Manga curta leve em malha de algodão — ideal para o verão',
        64.90, 'Manga Curta', true, 2, 2, 2, 4);

-- 16 Manga Longa Fleece menino
INSERT INTO pijama (nome, descricao, preco, modelo, ativo, sexo, categoria_id, marca_id, estampa_id)
VALUES ('Pijama Fleece Azul Menino', 'Manga longa em fleece, perfeito para noites de inverno',
        104.90, 'Manga Longa', true, 2, 2, 2, 4);

-- ── UNISSEX (categoria_id=3, marca_id=3 Julie e Bela) ──────

-- 17 Body unissex bebê
INSERT INTO pijama (nome, descricao, preco, modelo, ativo, sexo, categoria_id, marca_id, estampa_id)
VALUES ('Body Unissex Branco', 'Body liso branco para recém-nascido, em algodão 100% orgânico',
        44.90, 'Body', true, 3, 3, 3, 4);

-- 18 Macacão unissex Estrelas
INSERT INTO pijama (nome, descricao, preco, modelo, ativo, sexo, categoria_id, marca_id, estampa_id)
VALUES ('Macacão Estrelado', 'Macacão com estampas de estrelas, confortável para noites de frio',
        94.90, 'Macacão', true, 3, 3, 3, 3);

-- 19 Manga Curta unissex
INSERT INTO pijama (nome, descricao, preco, modelo, ativo, sexo, categoria_id, marca_id, estampa_id)
VALUES ('Pijama Unissex Verão', 'Manga curta leve e fresca, perfeito para o calor do verão',
        69.90, 'Manga Curta', true, 3, 3, 3, 4);

-- 20 Manga Longa Fleece unissex
INSERT INTO pijama (nome, descricao, preco, modelo, ativo, sexo, categoria_id, marca_id, estampa_id)
VALUES ('Pijama Fleece Unissex', 'Manga longa em fleece quentinho — ideal para o inverno',
        129.90, 'Manga Longa', true, 3, 3, 3, 4);

-- ── ADULTO (categoria_id=4) ─────────────────────────────────

-- 21 Manga Longa Adulto feminino
INSERT INTO pijama (nome, descricao, preco, modelo, ativo, sexo, categoria_id, marca_id, estampa_id)
VALUES ('Pijama Adulto Estrelas', 'Manga longa feminina com estampas delicadas de estrelas — algodão premium',
        119.90, 'Manga Longa', true, 1, 4, 1, 3);

-- 22 Manga Curta Adulto unissex
INSERT INTO pijama (nome, descricao, preco, modelo, ativo, sexo, categoria_id, marca_id, estampa_id)
VALUES ('Pijama Adulto Liso Verão', 'Manga curta em malha — confortável e leve para noites quentes',
        89.90, 'Manga Curta', true, 3, 4, 3, 4);

-- 23 Manga Longa Fleece Adulto masculino
INSERT INTO pijama (nome, descricao, preco, modelo, ativo, sexo, categoria_id, marca_id, estampa_id)
VALUES ('Pijama Adulto Fleece Inverno', 'Manga longa em fleece premium — máximo conforto no inverno',
        139.90, 'Manga Longa', true, 2, 4, 2, 4);

-- 24 Macacão Adulto feminino
INSERT INTO pijama (nome, descricao, preco, modelo, ativo, sexo, categoria_id, marca_id, estampa_id)
VALUES ('Macacão Adulto Rosa', 'Macacão adulto em malha macia — conforto e estilo em uma só peça',
        149.90, 'Macacão', true, 1, 4, 1, 4);

-- ── INATIVOS (mix de categorias/modelos) ───────────────────

-- 25 Manga Longa inativo menina
INSERT INTO pijama (nome, descricao, preco, modelo, ativo, sexo, categoria_id, marca_id, estampa_id)
VALUES ('Pijama Estrelas Amarelas', 'Manga longa com estampas de estrelas em amarelo vibrante',
        84.90, 'Manga Longa', false, 1, 1, 1, 3);

-- 26 Manga Curta inativo menino
INSERT INTO pijama (nome, descricao, preco, modelo, ativo, sexo, categoria_id, marca_id, estampa_id)
VALUES ('Pijama Dino Amarelo', 'Manga curta com dinossauros divertidos — edição limitada',
        74.90, 'Manga Curta', false, 2, 2, 2, 2);

-- 27 Macacão inativo unissex
INSERT INTO pijama (nome, descricao, preco, modelo, ativo, sexo, categoria_id, marca_id, estampa_id)
VALUES ('Macacão Liso Unissex', 'Macacão liso em algodão e malha — conforto total para os pequenos',
        79.90, 'Macacão', false, 3, 3, 3, 4);

-- ============================================================
-- 17. PIJAMA × MATERIAL (pijamas 8–27)
-- ============================================================
INSERT INTO pijama_material (pijama_id, material_id) VALUES (8,  1); -- Body Rosa         → Algodão
INSERT INTO pijama_material (pijama_id, material_id) VALUES (9,  1); -- Macacão Unicórnio → Algodão
INSERT INTO pijama_material (pijama_id, material_id) VALUES (9,  2); -- Macacão Unicórnio → Malha
INSERT INTO pijama_material (pijama_id, material_id) VALUES (10, 1); -- Estrelas Pink      → Algodão
INSERT INTO pijama_material (pijama_id, material_id) VALUES (11, 3); -- Fleece Roxo       → Fleece
INSERT INTO pijama_material (pijama_id, material_id) VALUES (12, 1); -- Body Azul         → Algodão
INSERT INTO pijama_material (pijama_id, material_id) VALUES (13, 1); -- Macacão Dino      → Algodão
INSERT INTO pijama_material (pijama_id, material_id) VALUES (13, 2); -- Macacão Dino      → Malha
INSERT INTO pijama_material (pijama_id, material_id) VALUES (14, 2); -- Clássico Azul     → Malha
INSERT INTO pijama_material (pijama_id, material_id) VALUES (15, 2); -- Verão Menino      → Malha
INSERT INTO pijama_material (pijama_id, material_id) VALUES (16, 3); -- Fleece Azul       → Fleece
INSERT INTO pijama_material (pijama_id, material_id) VALUES (17, 1); -- Body Unissex      → Algodão
INSERT INTO pijama_material (pijama_id, material_id) VALUES (18, 1); -- Macacão Estrelado → Algodão
INSERT INTO pijama_material (pijama_id, material_id) VALUES (18, 2); -- Macacão Estrelado → Malha
INSERT INTO pijama_material (pijama_id, material_id) VALUES (19, 1); -- Unissex Verão     → Algodão
INSERT INTO pijama_material (pijama_id, material_id) VALUES (20, 3); -- Fleece Unissex    → Fleece
INSERT INTO pijama_material (pijama_id, material_id) VALUES (21, 1); -- Adulto Estrelas   → Algodão
INSERT INTO pijama_material (pijama_id, material_id) VALUES (21, 2); -- Adulto Estrelas   → Malha
INSERT INTO pijama_material (pijama_id, material_id) VALUES (22, 2); -- Adulto Liso Verão → Malha
INSERT INTO pijama_material (pijama_id, material_id) VALUES (23, 3); -- Adulto Fleece     → Fleece
INSERT INTO pijama_material (pijama_id, material_id) VALUES (24, 2); -- Macacão Adulto    → Malha
INSERT INTO pijama_material (pijama_id, material_id) VALUES (25, 1); -- Estrelas Amarelas → Algodão
INSERT INTO pijama_material (pijama_id, material_id) VALUES (26, 2); -- Dino Amarelo      → Malha
INSERT INTO pijama_material (pijama_id, material_id) VALUES (27, 1); -- Macacão Liso      → Algodão
INSERT INTO pijama_material (pijama_id, material_id) VALUES (27, 2); -- Macacão Liso      → Malha

-- ============================================================
-- 18. PIJAMA × VARIANTE (pijamas 8–27)
-- Tamanhos: RN | UM_A_TRES_MESES | TRES_A_SEIS_MESES | SEIS_A_NOVE_MESES
--           UM_ANO | DOIS_ANOS | TRES_ANOS | QUATRO_ANOS | SEIS_ANOS
--           OITO_ANOS | DEZ_ANOS | DOZE_ANOS | ADULTO
-- ============================================================

-- Pijama 8 — Body Liso Rosa (bebê, tamanhos RN–SEIS_A_NOVE_MESES, cor Rosa=1)
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (8, 'RN',                1, 20);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (8, 'P',   1, 15);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (8, 'M', 1, 12);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (8, 'G', 1,  8);

-- Pijama 9 — Macacão Unicórnio (4–8 anos, Rosa=1 e Roxo=5)
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (9, 'QUATRO_ANOS', 1, 10);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (9, 'SEIS_ANOS',   1,  8);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (9, 'OITO_ANOS',   1,  6);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (9, 'QUATRO_ANOS', 5,  8);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (9, 'SEIS_ANOS',   5,  6);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (9, 'OITO_ANOS',   5,  4);

-- Pijama 10 — Estrelas Pink (6–12 anos, Rosa=1)
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (10, 'SEIS_ANOS',   1, 12);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (10, 'OITO_ANOS',   1, 10);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (10, 'DEZ_ANOS',    1,  8);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (10, 'DOZE_ANOS',   1,  5);

-- Pijama 11 — Fleece Roxo Menina (6–10 anos, Roxo=5)
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (11, 'SEIS_ANOS',  5,  8);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (11, 'OITO_ANOS',  5,  6);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (11, 'DEZ_ANOS',   5,  4);

-- Pijama 12 — Body Liso Azul (bebê, Azul=2)
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (12, 'RN',                2, 20);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (12, 'P',   2, 15);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (12, 'M', 2, 12);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (12, 'G', 2,  8);

-- Pijama 13 — Macacão Dino Aventura (4–8 anos, Azul=2 e Amarelo=3)
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (13, 'QUATRO_ANOS', 2, 10);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (13, 'SEIS_ANOS',   2,  8);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (13, 'OITO_ANOS',   2,  6);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (13, 'QUATRO_ANOS', 3,  6);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (13, 'SEIS_ANOS',   3,  4);

-- Pijama 14 — Clássico Azul (6–12 anos, Azul=2)
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (14, 'SEIS_ANOS',  2, 15);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (14, 'OITO_ANOS',  2, 12);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (14, 'DEZ_ANOS',   2,  8);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (14, 'DOZE_ANOS',  2,  5);

-- Pijama 15 — Verão Menino (4–10 anos, sem cor)
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (15, 'QUATRO_ANOS', null, 15);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (15, 'SEIS_ANOS',   null, 12);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (15, 'OITO_ANOS',   null, 10);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (15, 'DEZ_ANOS',    null,  6);

-- Pijama 16 — Fleece Azul Menino (6–10 anos, Azul=2)
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (16, 'SEIS_ANOS',  2,  8);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (16, 'OITO_ANOS',  2,  6);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (16, 'DEZ_ANOS',   2,  4);

-- Pijama 17 — Body Unissex Branco (bebê, sem cor)
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (17, 'RN',                null, 20);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (17, 'P',   null, 15);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (17, 'M', null, 12);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (17, 'G', null,  8);

-- Pijama 18 — Macacão Estrelado (2–6 anos, sem cor)
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (18, 'DOIS_ANOS',   null, 10);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (18, 'QUATRO_ANOS', null,  6);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (18, 'SEIS_ANOS',   null,  4);

-- Pijama 19 — Unissex Verão (6–12 anos, Branco=4)
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (19, 'SEIS_ANOS',  4, 12);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (19, 'OITO_ANOS',  4, 10);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (19, 'DEZ_ANOS',   4,  8);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (19, 'DOZE_ANOS',  4,  5);

-- Pijama 20 — Fleece Unissex (8–12 anos, sem cor)
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (20, 'OITO_ANOS',  null, 10);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (20, 'DEZ_ANOS',   null,  8);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (20, 'DOZE_ANOS',  null,  6);

-- Pijama 21 — Adulto Estrelas (tamanho ADULTO, Rosa=1 Roxo=5 Branco=4)
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (21, 'ADULTO', 1, 10);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (21, 'ADULTO', 5,  8);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (21, 'ADULTO', 4,  6);

-- Pijama 22 — Adulto Liso Verão (ADULTO, sem cor)
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (22, 'ADULTO', null, 15);

-- Pijama 23 — Adulto Fleece Inverno (ADULTO, Azul=2 Branco=4)
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (23, 'ADULTO', 2, 10);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (23, 'ADULTO', 4,  8);

-- Pijama 24 — Macacão Adulto Rosa (ADULTO, Rosa=1 Branco=4)
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (24, 'ADULTO', 1,  8);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (24, 'ADULTO', 4,  5);

-- Pijama 25 — Estrelas Amarelas / INATIVO (4–6 anos, Amarelo=3)
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (25, 'QUATRO_ANOS', 3,  5);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (25, 'SEIS_ANOS',   3,  3);

-- Pijama 26 — Dino Amarelo / INATIVO (6–8 anos, Amarelo=3)
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (26, 'SEIS_ANOS',  3,  4);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (26, 'OITO_ANOS',  3,  2);

-- Pijama 27 — Macacão Liso / INATIVO (4–6 anos, sem cor)
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (27, 'QUATRO_ANOS', null, 3);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (27, 'SEIS_ANOS',   null, 2);

-- ============================================================
-- 19. PIJAMAS ADICIONAIS (IDs 28–29)
-- Cobrem tamanhos UM_ANO, GG, XG, DEZESSEIS_ANOS
-- ============================================================

-- 28 Body 1 Ano — cobre UM_ANO
INSERT INTO pijama (nome, descricao, preco, modelo, ativo, sexo, categoria_id, marca_id, estampa_id)
VALUES ('Body 1 Ano Estrelado',
        'Body em algodão para bebê de 1 ano, com estampas de estrelas delicadas',
        54.90, 'Body', true, 3, 3, 3, 3);

-- 29 Pijama Juvenil Plus — cobre GG, XG, DEZESSEIS_ANOS
INSERT INTO pijama (nome, descricao, preco, modelo, ativo, sexo, categoria_id, marca_id, estampa_id)
VALUES ('Pijama Juvenil Plus',
        'Pijama manga longa em malha para tamanhos grandes e adolescentes',
        84.90, 'Manga Longa', true, 2, 2, 2, 4);

-- ============================================================
-- 20. PIJAMA × MATERIAL (pijamas 28–29)
-- ============================================================
INSERT INTO pijama_material (pijama_id, material_id) VALUES (28, 1); -- Body 1 Ano     → Algodão
INSERT INTO pijama_material (pijama_id, material_id) VALUES (29, 2); -- Juvenil Plus   → Malha

-- ============================================================
-- 21. PIJAMA × VARIANTE (pijamas 28–29)
-- ============================================================

-- Pijama 28 — Body 1 Ano (UM_ANO, Rosa=1 e Azul=2)
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (28, 'UM_ANO', 1, 15);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (28, 'UM_ANO', 2, 12);

-- Pijama 29 — Juvenil Plus (GG, XG, DEZESSEIS_ANOS, Azul=2 e Branco=4)
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (29, 'GG',             2, 12);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (29, 'XG',             2,  8);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (29, 'DEZESSEIS_ANOS', 2, 10);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (29, 'GG',             4,  6);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (29, 'XG',             4,  4);
