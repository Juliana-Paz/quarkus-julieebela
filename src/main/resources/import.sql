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
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (5, 'TRES_ANOS',   null,  8);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (5, 'QUATRO_ANOS', null,  6);

INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (6, 'SEIS_ANOS',   1,  8);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (6, 'OITO_ANOS',   1,  6);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (6, 'SEIS_ANOS',   5,  5);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (6, 'OITO_ANOS',   5,  4);

INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (7, 'RN',                null, 15);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (7, 'UM_A_TRES_MESES',   null, 12);
INSERT INTO pijama_variante (pijama_id, tamanho, cor_id, estoque) VALUES (7, 'TRES_A_SEIS_MESES', null,  8);

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
