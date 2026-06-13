-- ============================================================
-- import.sql — Ateliê Julie & Bela
-- Gerado para Quarkus 3 + Hibernate 6 (PhysicalNamingStrategyStandardImpl)
-- Nomes de tabela: tudo minúsculo, sem underscores (ex: pijamavariante)
-- Enums via Converter: INTEGER no banco (exceto Tamanho: VARCHAR)
-- Senhas: PBKDF2WithHmacSHA512 + salt #blahxyz17
-- ============================================================

-- ------------------------------------------------------------
-- MARCA
-- ------------------------------------------------------------
INSERT INTO marca (id, nome, descricao, datacadastro, dataalteracao) VALUES
(1, 'Ateliê Julie & Bela', 'Marca exclusiva de pijamas infantis e família com carinho e qualidade', NOW(), NOW());

-- ------------------------------------------------------------
-- CATEGORIA
-- ------------------------------------------------------------
INSERT INTO categoria (id, nome, descricao, datacadastro, dataalteracao) VALUES
(1,  'Body Bebê',        'Bodies para recém-nascidos e bebês',                     NOW(), NOW()),
(2,  'Macacão',          'Macacões para bebês e crianças pequenas',                NOW(), NOW()),
(3,  'Pijama Infantil',  'Pijamas para crianças de 1 a 10 anos',                  NOW(), NOW()),
(4,  'Pijama Juvenil',   'Pijamas para crianças maiores e adolescentes',           NOW(), NOW()),
(5,  'Pijama Adulto',    'Pijamas confortáveis para adultos',                      NOW(), NOW()),
(6,  'Pijama Família',   'Conjuntos combinando para toda a família',               NOW(), NOW()),
(7,  'Coleção Natal',    'Pijamas temáticos natalinos para todas as idades',       NOW(), NOW());

-- ------------------------------------------------------------
-- COR
-- ------------------------------------------------------------
INSERT INTO cor (id, nome, hexadecimal, datacadastro, dataalteracao) VALUES
(1,  'Rosa',        '#FFB6C1', NOW(), NOW()),
(2,  'Lilás',       '#C8A2C8', NOW(), NOW()),
(3,  'Azul',        '#AED6F1', NOW(), NOW()),
(4,  'Azul Marinho','#1B2A4A', NOW(), NOW()),
(5,  'Branco',      '#FFFFFF', NOW(), NOW()),
(6,  'Cinza',       '#AAAAAA', NOW(), NOW()),
(7,  'Bege',        '#F5F0E8', NOW(), NOW()),
(8,  'Vermelho',    '#E74C3C', NOW(), NOW()),
(9,  'Verde',       '#58D68D', NOW(), NOW()),
(10, 'Amarelo',     '#F9E79F', NOW(), NOW());

-- ------------------------------------------------------------
-- MATERIAL
-- ------------------------------------------------------------
INSERT INTO material (id, nome, descricao, datacadastro, dataalteracao) VALUES
(1, 'Algodão',     '100% algodão, suave e respirável',                    NOW(), NOW()),
(2, 'Suedine',     'Tecido macio e quentinho, ideal para bebês',          NOW(), NOW()),
(3, 'Cotton',      'Cotton penteado de alta qualidade',                   NOW(), NOW()),
(4, 'Flanela',     'Flanela quente para noites frias',                    NOW(), NOW()),
(5, 'Microfibra',  'Microfibra ultramacia e de secagem rápida',           NOW(), NOW());

-- ------------------------------------------------------------
-- ESTAMPA
-- ------------------------------------------------------------
INSERT INTO estampa (id, nome, datacadastro, dataalteracao) VALUES
(1,  'Unicórnio',    NOW(), NOW()),
(2,  'Dinossauro',   NOW(), NOW()),
(3,  'Ursinho',      NOW(), NOW()),
(4,  'Astronauta',   NOW(), NOW()),
(5,  'Arco-íris',    NOW(), NOW()),
(6,  'Flores',       NOW(), NOW()),
(7,  'Corações',     NOW(), NOW()),
(8,  'Estrelas',     NOW(), NOW()),
(9,  'Xadrez',       NOW(), NOW()),
(10, 'Natal',        NOW(), NOW()),
(11, 'Liso',         NOW(), NOW());

-- ------------------------------------------------------------
-- ESTADO (Tocantins + alguns para endereços de clientes)
-- regiao: 1=Centro-Oeste 2=Norte 3=Nordeste 4=Sudeste 5=Sul
-- ------------------------------------------------------------
INSERT INTO estado (id, nome, sigla, regiao, datacadastro, dataalteracao) VALUES
(1,  'Tocantins',       'TO', 2, NOW(), NOW()),
(2,  'São Paulo',       'SP', 4, NOW(), NOW()),
(3,  'Minas Gerais',    'MG', 4, NOW(), NOW()),
(4,  'Rio de Janeiro',  'RJ', 4, NOW(), NOW()),
(5,  'Goiás',           'GO', 1, NOW(), NOW());

-- ------------------------------------------------------------
-- MUNICÍPIO
-- ------------------------------------------------------------
INSERT INTO municipio (id, nome, id_estado, datacadastro, dataalteracao) VALUES
(1,  'Palmas',              1, NOW(), NOW()),
(2,  'Araguaína',           1, NOW(), NOW()),
(3,  'São Paulo',           2, NOW(), NOW()),
(4,  'Belo Horizonte',      3, NOW(), NOW()),
(5,  'Rio de Janeiro',      4, NOW(), NOW());

-- ------------------------------------------------------------
-- USUARIO
-- perfil: 1=Adm, 2=User
-- senhas PBKDF2WithHmacSHA512 + salt #blahxyz17
--   admin123  → SYu34Plo5KZGE9fMtUK9LRPnWC3WvVpogVg35bf5tPYMM6dxXNV6AWmPEQzOLc110uIwcv8TOigbaCB43f8KHQ==
--   123456    → 0cctg7WgpEz7kC/AzVC+KX+bZLPXDtgJDqWWZWnmzHH+7Na2YVxYYSFPxcf7ImAjqfNckx0aT4n5qKM7WEoeEQ==
-- ------------------------------------------------------------
INSERT INTO usuario (id, nome, username, senha, perfil, datacadastro, dataalteracao) VALUES
(1, 'Administrador',  'admin',    'SYu34Plo5KZGE9fMtUK9LRPnWC3WvVpogVg35bf5tPYMM6dxXNV6AWmPEQzOLc110uIwcv8TOigbaCB43f8KHQ==', 1, NOW(), NOW()),
(2, 'Ana Lima',       'ana',      '0cctg7WgpEz7kC/AzVC+KX+bZLPXDtgJDqWWZWnmzHH+7Na2YVxYYSFPxcf7ImAjqfNckx0aT4n5qKM7WEoeEQ==', 2, NOW(), NOW()),
(3, 'Bruno Souza',    'bruno',    '0cctg7WgpEz7kC/AzVC+KX+bZLPXDtgJDqWWZWnmzHH+7Na2YVxYYSFPxcf7ImAjqfNckx0aT4n5qKM7WEoeEQ==', 2, NOW(), NOW()),
(4, 'Carla Mendes',   'carla',    '0cctg7WgpEz7kC/AzVC+KX+bZLPXDtgJDqWWZWnmzHH+7Na2YVxYYSFPxcf7ImAjqfNckx0aT4n5qKM7WEoeEQ==', 2, NOW(), NOW()),
(5, 'Daniel Costa',   'daniel',   '0cctg7WgpEz7kC/AzVC+KX+bZLPXDtgJDqWWZWnmzHH+7Na2YVxYYSFPxcf7ImAjqfNckx0aT4n5qKM7WEoeEQ==', 2, NOW(), NOW());

-- ------------------------------------------------------------
-- CLIENTE
-- ------------------------------------------------------------
INSERT INTO cliente (id, nome, cpf, email, data_nascimento, usuario_id, datacadastro, dataalteracao) VALUES
(1, 'Ana Lima',     '11122233344', 'ana@email.com',    '1990-03-15', 2, NOW(), NOW()),
(2, 'Bruno Souza',  '22233344455', 'bruno@email.com',  '1985-07-22', 3, NOW(), NOW()),
(3, 'Carla Mendes', '33344455566', 'carla@email.com',  '1993-11-08', 4, NOW(), NOW()),
(4, 'Daniel Costa', '44455566677', 'daniel@email.com', '1988-01-30', 5, NOW(), NOW());

-- ------------------------------------------------------------
-- CLIENTE TELEFONE
-- ------------------------------------------------------------
INSERT INTO cliente_telefone (cliente_id, codigo_area, numero) VALUES
(1, '63', '991234567'),
(2, '11', '987654321'),
(3, '31', '996543210'),
(4, '21', '998877665');

-- ------------------------------------------------------------
-- CLIENTE ENDEREÇO
-- ------------------------------------------------------------
INSERT INTO cliente_endereco (cliente_id, logradouro, numero, complemento, bairro, cep, municipio, estado, principal) VALUES
(1, 'Quadra 103 Sul Rua SE 03', '15',  'Apto 202',  'Plano Diretor Sul', '77020-014', 'Palmas',           'Tocantins',    true),
(2, 'Rua das Flores',           '200', NULL,         'Jardim Paulista',   '01310-000', 'São Paulo',         'São Paulo',    true),
(3, 'Av. Afonso Pena',          '500', 'Sala 10',    'Centro',            '30130-001', 'Belo Horizonte',    'Minas Gerais', true),
(4, 'Rua da Praia',             '77',  'Casa',       'Copacabana',        '22070-011', 'Rio de Janeiro',    'Rio de Janeiro', true);

-- ------------------------------------------------------------
-- CUPOM DESCONTO
-- ------------------------------------------------------------
INSERT INTO cupomdesconto (id, codigo, descricao, valor_desconto, percentual, data_inicio, data_fim, ativo, datacadastro, dataalteracao) VALUES
(1, 'BEMVINDO10',   'Desconto de 10% na primeira compra',       10.00, true,  '2025-01-01', '2026-12-31', true,  NOW(), NOW()),
(2, 'NATAL15',      'Desconto de 15% na Coleção Natal',         15.00, true,  '2025-11-01', '2026-01-15', true,  NOW(), NOW()),
(3, 'FAMILIA20',    'Desconto de 20% em Pijamas Família',       20.00, true,  '2025-01-01', '2026-12-31', true,  NOW(), NOW()),
(4, 'FRETEGRATIS',  'Frete grátis em compras acima de R$ 150',  0.00,  false, '2025-01-01', '2026-12-31', true,  NOW(), NOW());

-- ============================================================
-- PIJAMAS
-- sexo: 1=Feminino 2=Masculino 3=Unissex
-- estampa_id nullable (1 produto sem estampa)
-- 1 produto inativo
-- modelo mantido como NULL (campo existe mas não usamos)
-- ============================================================

-- ---- BODY BEBÊ (cat 1) — 4 produtos ----
INSERT INTO pijama (id, nome, descricao, preco, modelo, ativo, sexo, categoria_id, marca_id, estampa_id, datacadastro, dataalteracao) VALUES
(1,  'Body Bebê Unicórnio Rosa',      'Body manga curta em suedine com estampa de unicórnio',     49.90, NULL, true,  1, 1, 1, 1,    NOW(), NOW()),
(2,  'Body Bebê Dinossauro Azul',     'Body manga longa em algodão com estampa de dinossauro',    44.90, NULL, true,  2, 1, 1, 2,    NOW(), NOW()),
(3,  'Body Bebê Ursinho Bege',        'Body sem manga em suedine tom neutro com ursinho',         39.90, NULL, true,  3, 1, 1, 3,    NOW(), NOW()),
(4,  'Body Bebê Liso Branco',         'Body básico liso branco 100% algodão — sem estampa',       42.90, NULL, true,  3, 1, 1, NULL, NOW(), NOW());  -- sem estampa

-- ---- MACACÃO (cat 2) — 6 produtos ----
INSERT INTO pijama (id, nome, descricao, preco, modelo, ativo, sexo, categoria_id, marca_id, estampa_id, datacadastro, dataalteracao) VALUES
(5,  'Macacão Astronauta Cinza',      'Macacão manga longa em cotton estampa astronauta',         69.90, NULL, true,  2, 2, 1, 4,    NOW(), NOW()),
(6,  'Macacão Arco-íris Lilás',       'Macacão em suedine com estampa arco-íris colorida',        64.90, NULL, true,  1, 2, 1, 5,    NOW(), NOW()),
(7,  'Macacão Flores Rosa',           'Macacão delicado em algodão com florais bordados',          59.90, NULL, true,  1, 2, 1, 6,    NOW(), NOW()),
(8,  'Macacão Xadrez Vermelho',       'Macacão xadrez quentinho em flanela',                       74.90, NULL, true,  3, 2, 1, 9,    NOW(), NOW()),
(9,  'Macacão Ursinho Verde',         'Macacão em microfibra com ursinho bordado no peito',        79.90, NULL, true,  2, 2, 1, 3,    NOW(), NOW()),
(10, 'Macacão Liso Azul Marinho',     'Macacão liso azul marinho em algodão premium',             49.90, NULL, false, 2, 2, 1, NULL, NOW(), NOW());  -- inativo + sem estampa

-- ---- PIJAMA INFANTIL (cat 3) — 10 produtos ----
INSERT INTO pijama (id, nome, descricao, preco, modelo, ativo, sexo, categoria_id, marca_id, estampa_id, datacadastro, dataalteracao) VALUES
(11, 'Pijama Infantil Unicórnio',     'Conjunto calça e blusa em cotton com unicórnio',            79.90, NULL, true,  1, 3, 1, 1,    NOW(), NOW()),
(12, 'Pijama Infantil Dinossauro',    'Conjunto manga longa dinossauro em algodão',                74.90, NULL, true,  2, 3, 1, 2,    NOW(), NOW()),
(13, 'Pijama Infantil Estrelas',      'Conjunto calça e blusa estampa estrelas cintilantes',       69.90, NULL, true,  3, 3, 1, 8,    NOW(), NOW()),
(14, 'Pijama Infantil Astronauta',    'Conjunto infantil temático espaço em microfibra',           84.90, NULL, true,  2, 3, 1, 4,    NOW(), NOW()),
(15, 'Pijama Infantil Arco-íris',     'Conjunto colorido arco-íris em cotton penteado',            79.90, NULL, true,  1, 3, 1, 5,    NOW(), NOW()),
(16, 'Pijama Infantil Corações',      'Conjunto rosa com corações em suedine macio',               74.90, NULL, true,  1, 3, 1, 7,    NOW(), NOW()),
(17, 'Pijama Infantil Ursinho',       'Conjunto clássico ursinho em algodão premium',              69.90, NULL, true,  3, 3, 1, 3,    NOW(), NOW()),
(18, 'Pijama Infantil Flores',        'Conjunto florido em cotton suave para meninas',             79.90, NULL, true,  1, 3, 1, 6,    NOW(), NOW()),
(19, 'Pijama Infantil Xadrez Azul',   'Conjunto xadrez azul em flanela quentinha',                84.90, NULL, true,  2, 3, 1, 9,    NOW(), NOW()),
(20, 'Pijama Infantil Liso Cinza',    'Conjunto liso cinza básico em algodão',                     59.90, NULL, true,  3, 3, 1, 11,   NOW(), NOW());

-- ---- PIJAMA JUVENIL (cat 4) — 5 produtos ----
INSERT INTO pijama (id, nome, descricao, preco, modelo, ativo, sexo, categoria_id, marca_id, estampa_id, datacadastro, dataalteracao) VALUES
(21, 'Pijama Juvenil Estrelas',       'Conjunto juvenil estrelas em microfibra suave',             89.90, NULL, true,  3, 4, 1, 8,    NOW(), NOW()),
(22, 'Pijama Juvenil Astronauta',     'Conjunto temático espaço para jovens em cotton',           99.90, NULL, true,  2, 4, 1, 4,    NOW(), NOW()),
(23, 'Pijama Juvenil Flores',         'Conjunto floral juvenil em algodão penteado',               94.90, NULL, true,  1, 4, 1, 6,    NOW(), NOW()),
(24, 'Pijama Juvenil Xadrez',         'Conjunto xadrez clássico em flanela para jovens',         109.90, NULL, true,  3, 4, 1, 9,    NOW(), NOW()),
(25, 'Pijama Juvenil Liso Azul',      'Conjunto liso azul marinho em cotton premium',             119.90, NULL, true,  2, 4, 1, 11,   NOW(), NOW());

-- ---- PIJAMA ADULTO (cat 5) — 5 produtos ----
INSERT INTO pijama (id, nome, descricao, preco, modelo, ativo, sexo, categoria_id, marca_id, estampa_id, datacadastro, dataalteracao) VALUES
(26, 'Pijama Adulto Flores',          'Conjunto adulto floral em algodão premium',                109.90, NULL, true,  1, 5, 1, 6,    NOW(), NOW()),
(27, 'Pijama Adulto Estrelas',        'Conjunto adulto estrelas em microfibra premium',           119.90, NULL, true,  3, 5, 1, 8,    NOW(), NOW()),
(28, 'Pijama Adulto Xadrez Flanela',  'Conjunto adulto xadrez em flanela quente',                129.90, NULL, true,  3, 5, 1, 9,    NOW(), NOW()),
(29, 'Pijama Adulto Liso Rosa',       'Conjunto adulto liso rosa em cotton penteado',             99.90, NULL, true,  1, 5, 1, 11,   NOW(), NOW()),
(30, 'Pijama Adulto Liso Cinza',      'Conjunto adulto liso cinza em algodão premium',           139.90, NULL, true,  2, 5, 1, 11,   NOW(), NOW());

-- ---- PIJAMA FAMÍLIA (cat 6) — 3 produtos ----
INSERT INTO pijama (id, nome, descricao, preco, modelo, ativo, sexo, categoria_id, marca_id, estampa_id, datacadastro, dataalteracao) VALUES
(31, 'Pijama Família Ursinho',        'Kit família combinando ursinho em algodão macio',          149.90, NULL, true,  3, 6, 1, 3,    NOW(), NOW()),
(32, 'Pijama Família Estrelas',       'Kit família estrelas em microfibra ultramacia',            159.90, NULL, true,  3, 6, 1, 8,    NOW(), NOW()),
(33, 'Pijama Família Xadrez',         'Kit família xadrez clássico em flanela quentinha',        149.90, NULL, true,  3, 6, 1, 9,    NOW(), NOW());

-- ---- COLEÇÃO NATAL (cat 7) — 3 produtos ----
INSERT INTO pijama (id, nome, descricao, preco, modelo, ativo, sexo, categoria_id, marca_id, estampa_id, datacadastro, dataalteracao) VALUES
(34, 'Natal Bebê Papai Noel',         'Body natalino especial para bebê em suedine',               59.90, NULL, true,  3, 7, 1, 10,   NOW(), NOW()),
(35, 'Natal Infantil Renas',          'Pijama natalino infantil com renas em cotton',              89.90, NULL, true,  3, 7, 1, 10,   NOW(), NOW()),
(36, 'Natal Família Xadrez Vermelho', 'Kit família natal xadrez vermelho em flanela',             149.90, NULL, true,  3, 7, 1, 10,   NOW(), NOW());

-- ============================================================
-- PIJAMA → MATERIAL (join table)
-- ============================================================
INSERT INTO pijama_material (pijama_id, material_id) VALUES
-- Body Bebê
(1, 2),(2, 1),(3, 2),(4, 1),
-- Macacão
(5, 3),(6, 2),(7, 1),(8, 4),(9, 5),(10, 1),
-- Infantil
(11, 3),(12, 1),(13, 3),(14, 5),(15, 3),(16, 2),(17, 1),(18, 3),(19, 4),(20, 1),
-- Juvenil
(21, 5),(22, 3),(23, 1),(24, 4),(25, 3),
-- Adulto
(26, 1),(27, 5),(28, 4),(29, 3),(30, 1),
-- Família
(31, 1),(32, 5),(33, 4),
-- Natal
(34, 2),(35, 3),(36, 4);

-- ============================================================
-- PIJAMA VARIANTE
-- Tabela: pijamavariante (sem underscore — naming strategy Hibernate 6)
-- tamanho: VARCHAR com nome do enum Java
-- cor_id nullable (1 produto sem cor = id 10, produto "sem cor" = produto 4)
-- ~150 variantes distribuídas
-- estoque: 70% 5-20 | 20% 1-3 | 10% 0
-- ============================================================

-- ---- Body Bebê (tamanhos: RN, P, M) ----

-- Produto 1: Body Bebê Unicórnio Rosa — 3 variantes
INSERT INTO pijamavariante (pijama_id, tamanho, cor_id, estoque, datacadastro, dataalteracao) VALUES
(1, 'RN', 1,  12, NOW(), NOW()),
(1, 'P',  1,  8,  NOW(), NOW()),
(1, 'M',  1,  0,  NOW(), NOW());  -- estoque 0

-- Produto 2: Body Bebê Dinossauro Azul — 3 variantes
INSERT INTO pijamavariante (pijama_id, tamanho, cor_id, estoque, datacadastro, dataalteracao) VALUES
(2, 'RN', 3,  15, NOW(), NOW()),
(2, 'P',  3,  10, NOW(), NOW()),
(2, 'M',  3,  2,  NOW(), NOW());  -- estoque baixo

-- Produto 3: Body Bebê Ursinho Bege — 3 variantes
INSERT INTO pijamavariante (pijama_id, tamanho, cor_id, estoque, datacadastro, dataalteracao) VALUES
(3, 'RN', 7,  18, NOW(), NOW()),
(3, 'P',  7,  14, NOW(), NOW()),
(3, 'M',  7,  6,  NOW(), NOW());

-- Produto 4: Body Bebê Liso Branco — 1 variante (produto com 1 variante, sem cor)
INSERT INTO pijamavariante (pijama_id, tamanho, cor_id, estoque, datacadastro, dataalteracao) VALUES
(4, 'RN', NULL, 20, NOW(), NOW());  -- sem cor, 1 única variante

-- ---- Macacão (tamanhos: RN, P, M, G) ----

-- Produto 5: Macacão Astronauta Cinza — 4 variantes
INSERT INTO pijamavariante (pijama_id, tamanho, cor_id, estoque, datacadastro, dataalteracao) VALUES
(5, 'RN', 6,  10, NOW(), NOW()),
(5, 'P',  6,  8,  NOW(), NOW()),
(5, 'M',  6,  5,  NOW(), NOW()),
(5, 'G',  6,  1,  NOW(), NOW());  -- estoque baixo

-- Produto 6: Macacão Arco-íris Lilás — 4 variantes
INSERT INTO pijamavariante (pijama_id, tamanho, cor_id, estoque, datacadastro, dataalteracao) VALUES
(6, 'RN', 2,  12, NOW(), NOW()),
(6, 'P',  2,  9,  NOW(), NOW()),
(6, 'M',  2,  7,  NOW(), NOW()),
(6, 'G',  2,  0,  NOW(), NOW());  -- estoque 0

-- Produto 7: Macacão Flores Rosa — 4 variantes
INSERT INTO pijamavariante (pijama_id, tamanho, cor_id, estoque, datacadastro, dataalteracao) VALUES
(7, 'RN', 1,  15, NOW(), NOW()),
(7, 'P',  1,  11, NOW(), NOW()),
(7, 'M',  1,  8,  NOW(), NOW()),
(7, 'G',  1,  3,  NOW(), NOW());

-- Produto 8: Macacão Xadrez Vermelho — 4 variantes
INSERT INTO pijamavariante (pijama_id, tamanho, cor_id, estoque, datacadastro, dataalteracao) VALUES
(8, 'RN', 8,  6,  NOW(), NOW()),
(8, 'P',  8,  10, NOW(), NOW()),
(8, 'M',  8,  14, NOW(), NOW()),
(8, 'G',  8,  2,  NOW(), NOW());

-- Produto 9: Macacão Ursinho Verde — 4 variantes
INSERT INTO pijamavariante (pijama_id, tamanho, cor_id, estoque, datacadastro, dataalteracao) VALUES
(9, 'RN', 9,  7,  NOW(), NOW()),
(9, 'P',  9,  12, NOW(), NOW()),
(9, 'M',  9,  5,  NOW(), NOW()),
(9, 'G',  9,  0,  NOW(), NOW());  -- estoque 0

-- Produto 10: Macacão Azul Marinho (INATIVO) — 4 variantes
INSERT INTO pijamavariante (pijama_id, tamanho, cor_id, estoque, datacadastro, dataalteracao) VALUES
(10, 'RN', 4,  5,  NOW(), NOW()),
(10, 'P',  4,  3,  NOW(), NOW()),
(10, 'M',  4,  2,  NOW(), NOW()),
(10, 'G',  4,  1,  NOW(), NOW());

-- ---- Pijama Infantil (tamanhos: UM_ANO, DOIS_ANOS, QUATRO_ANOS, SEIS_ANOS, OITO_ANOS, DEZ_ANOS) ----

-- Produto 11: Unicórnio — 6 variantes
INSERT INTO pijamavariante (pijama_id, tamanho, cor_id, estoque, datacadastro, dataalteracao) VALUES
(11, 'UM_ANO',      1, 10, NOW(), NOW()),
(11, 'DOIS_ANOS',   1, 8,  NOW(), NOW()),
(11, 'QUATRO_ANOS', 1, 12, NOW(), NOW()),
(11, 'SEIS_ANOS',   1, 7,  NOW(), NOW()),
(11, 'OITO_ANOS',   1, 5,  NOW(), NOW()),
(11, 'DEZ_ANOS',    1, 0,  NOW(), NOW());  -- estoque 0

-- Produto 12: Dinossauro — 6 variantes
INSERT INTO pijamavariante (pijama_id, tamanho, cor_id, estoque, datacadastro, dataalteracao) VALUES
(12, 'UM_ANO',      3, 9,  NOW(), NOW()),
(12, 'DOIS_ANOS',   3, 14, NOW(), NOW()),
(12, 'QUATRO_ANOS', 3, 6,  NOW(), NOW()),
(12, 'SEIS_ANOS',   3, 11, NOW(), NOW()),
(12, 'OITO_ANOS',   3, 3,  NOW(), NOW()),
(12, 'DEZ_ANOS',    3, 1,  NOW(), NOW());

-- Produto 13: Estrelas — 6 variantes (cores mistas)
INSERT INTO pijamavariante (pijama_id, tamanho, cor_id, estoque, datacadastro, dataalteracao) VALUES
(13, 'UM_ANO',      4,  8,  NOW(), NOW()),
(13, 'DOIS_ANOS',   4,  15, NOW(), NOW()),
(13, 'QUATRO_ANOS', 4,  10, NOW(), NOW()),
(13, 'SEIS_ANOS',   10, 6,  NOW(), NOW()),
(13, 'OITO_ANOS',   10, 2,  NOW(), NOW()),
(13, 'DEZ_ANOS',    10, 0,  NOW(), NOW());  -- estoque 0

-- Produto 14: Astronauta — 5 variantes
INSERT INTO pijamavariante (pijama_id, tamanho, cor_id, estoque, datacadastro, dataalteracao) VALUES
(14, 'DOIS_ANOS',   6,  7,  NOW(), NOW()),
(14, 'QUATRO_ANOS', 6,  12, NOW(), NOW()),
(14, 'SEIS_ANOS',   6,  9,  NOW(), NOW()),
(14, 'OITO_ANOS',   6,  5,  NOW(), NOW()),
(14, 'DEZ_ANOS',    6,  1,  NOW(), NOW());

-- Produto 15: Arco-íris — 5 variantes
INSERT INTO pijamavariante (pijama_id, tamanho, cor_id, estoque, datacadastro, dataalteracao) VALUES
(15, 'UM_ANO',      2, 10, NOW(), NOW()),
(15, 'DOIS_ANOS',   2, 8,  NOW(), NOW()),
(15, 'QUATRO_ANOS', 2, 14, NOW(), NOW()),
(15, 'SEIS_ANOS',   2, 3,  NOW(), NOW()),
(15, 'DEZ_ANOS',    2, 1,  NOW(), NOW());

-- Produto 16: Corações — 5 variantes
INSERT INTO pijamavariante (pijama_id, tamanho, cor_id, estoque, datacadastro, dataalteracao) VALUES
(16, 'UM_ANO',      1, 12, NOW(), NOW()),
(16, 'DOIS_ANOS',   1, 9,  NOW(), NOW()),
(16, 'QUATRO_ANOS', 1, 7,  NOW(), NOW()),
(16, 'SEIS_ANOS',   1, 4,  NOW(), NOW()),
(16, 'OITO_ANOS',   1, 0,  NOW(), NOW());  -- estoque 0

-- Produto 17: Ursinho — 4 variantes
INSERT INTO pijamavariante (pijama_id, tamanho, cor_id, estoque, datacadastro, dataalteracao) VALUES
(17, 'UM_ANO',      7, 8,  NOW(), NOW()),
(17, 'DOIS_ANOS',   7, 11, NOW(), NOW()),
(17, 'QUATRO_ANOS', 7, 6,  NOW(), NOW()),
(17, 'SEIS_ANOS',   7, 2,  NOW(), NOW());

-- Produto 18: Flores — 4 variantes
INSERT INTO pijamavariante (pijama_id, tamanho, cor_id, estoque, datacadastro, dataalteracao) VALUES
(18, 'DOIS_ANOS',   1, 10, NOW(), NOW()),
(18, 'QUATRO_ANOS', 1, 8,  NOW(), NOW()),
(18, 'SEIS_ANOS',   1, 5,  NOW(), NOW()),
(18, 'OITO_ANOS',   1, 1,  NOW(), NOW());

-- Produto 19: Xadrez Azul — 4 variantes
INSERT INTO pijamavariante (pijama_id, tamanho, cor_id, estoque, datacadastro, dataalteracao) VALUES
(19, 'QUATRO_ANOS', 3, 9,  NOW(), NOW()),
(19, 'SEIS_ANOS',   3, 7,  NOW(), NOW()),
(19, 'OITO_ANOS',   3, 12, NOW(), NOW()),
(19, 'DEZ_ANOS',    3, 3,  NOW(), NOW());

-- Produto 20: Liso Cinza — 4 variantes
INSERT INTO pijamavariante (pijama_id, tamanho, cor_id, estoque, datacadastro, dataalteracao) VALUES
(20, 'UM_ANO',      6, 15, NOW(), NOW()),
(20, 'DOIS_ANOS',   6, 10, NOW(), NOW()),
(20, 'QUATRO_ANOS', 6, 7,  NOW(), NOW()),
(20, 'SEIS_ANOS',   6, 2,  NOW(), NOW());

-- ---- Pijama Juvenil (tamanhos: OITO_ANOS, DEZ_ANOS, DOZE_ANOS, DEZESSEIS_ANOS) ----

-- Produto 21: Estrelas Juvenil — 4 variantes
INSERT INTO pijamavariante (pijama_id, tamanho, cor_id, estoque, datacadastro, dataalteracao) VALUES
(21, 'OITO_ANOS',      4, 8,  NOW(), NOW()),
(21, 'DEZ_ANOS',       4, 10, NOW(), NOW()),
(21, 'DOZE_ANOS',      4, 6,  NOW(), NOW()),
(21, 'DEZESSEIS_ANOS', 4, 2,  NOW(), NOW());

-- Produto 22: Astronauta Juvenil — 4 variantes
INSERT INTO pijamavariante (pijama_id, tamanho, cor_id, estoque, datacadastro, dataalteracao) VALUES
(22, 'OITO_ANOS',      6,  9,  NOW(), NOW()),
(22, 'DEZ_ANOS',       6,  12, NOW(), NOW()),
(22, 'DOZE_ANOS',      6,  7,  NOW(), NOW()),
(22, 'DEZESSEIS_ANOS', 6,  0,  NOW(), NOW());  -- estoque 0

-- Produto 23: Flores Juvenil — 4 variantes
INSERT INTO pijamavariante (pijama_id, tamanho, cor_id, estoque, datacadastro, dataalteracao) VALUES
(23, 'OITO_ANOS',      1, 6,  NOW(), NOW()),
(23, 'DEZ_ANOS',       1, 9,  NOW(), NOW()),
(23, 'DOZE_ANOS',      1, 11, NOW(), NOW()),
(23, 'DEZESSEIS_ANOS', 1, 4,  NOW(), NOW());

-- Produto 24: Xadrez Juvenil — 4 variantes (cores mistas)
INSERT INTO pijamavariante (pijama_id, tamanho, cor_id, estoque, datacadastro, dataalteracao) VALUES
(24, 'OITO_ANOS',      8, 10, NOW(), NOW()),
(24, 'DEZ_ANOS',       8, 8,  NOW(), NOW()),
(24, 'DOZE_ANOS',      3, 5,  NOW(), NOW()),
(24, 'DEZESSEIS_ANOS', 3, 1,  NOW(), NOW());

-- Produto 25: Liso Azul Juvenil — 3 variantes
INSERT INTO pijamavariante (pijama_id, tamanho, cor_id, estoque, datacadastro, dataalteracao) VALUES
(25, 'DEZ_ANOS',       4, 8,  NOW(), NOW()),
(25, 'DOZE_ANOS',      4, 5,  NOW(), NOW()),
(25, 'DEZESSEIS_ANOS', 4, 2,  NOW(), NOW());

-- ---- Pijama Adulto (tamanhos: P, M, G, GG, XG, ADULTO) ----

-- Produto 26: Flores Adulto — 5 variantes
INSERT INTO pijamavariante (pijama_id, tamanho, cor_id, estoque, datacadastro, dataalteracao) VALUES
(26, 'P',     1, 7,  NOW(), NOW()),
(26, 'M',     1, 12, NOW(), NOW()),
(26, 'G',     1, 9,  NOW(), NOW()),
(26, 'GG',    1, 5,  NOW(), NOW()),
(26, 'XG',    1, 2,  NOW(), NOW());

-- Produto 27: Estrelas Adulto — 5 variantes
INSERT INTO pijamavariante (pijama_id, tamanho, cor_id, estoque, datacadastro, dataalteracao) VALUES
(27, 'P',     4, 10, NOW(), NOW()),
(27, 'M',     4, 8,  NOW(), NOW()),
(27, 'G',     4, 6,  NOW(), NOW()),
(27, 'GG',    4, 3,  NOW(), NOW()),
(27, 'XG',    4, 1,  NOW(), NOW());

-- Produto 28: Xadrez Flanela Adulto — 5 variantes
INSERT INTO pijamavariante (pijama_id, tamanho, cor_id, estoque, datacadastro, dataalteracao) VALUES
(28, 'P',     8, 9,  NOW(), NOW()),
(28, 'M',     8, 14, NOW(), NOW()),
(28, 'G',     8, 11, NOW(), NOW()),
(28, 'GG',    8, 5,  NOW(), NOW()),
(28, 'XG',    8, 0,  NOW(), NOW());  -- estoque 0

-- Produto 29: Liso Rosa Adulto — 5 variantes
INSERT INTO pijamavariante (pijama_id, tamanho, cor_id, estoque, datacadastro, dataalteracao) VALUES
(29, 'P',     1, 12, NOW(), NOW()),
(29, 'M',     1, 10, NOW(), NOW()),
(29, 'G',     1, 8,  NOW(), NOW()),
(29, 'GG',    1, 3,  NOW(), NOW()),
(29, 'XG',    1, 1,  NOW(), NOW());

-- Produto 30: Liso Cinza Adulto — 5 variantes
INSERT INTO pijamavariante (pijama_id, tamanho, cor_id, estoque, datacadastro, dataalteracao) VALUES
(30, 'P',     6, 7,  NOW(), NOW()),
(30, 'M',     6, 15, NOW(), NOW()),
(30, 'G',     6, 9,  NOW(), NOW()),
(30, 'GG',    6, 4,  NOW(), NOW()),
(30, 'XG',    6, 1,  NOW(), NOW());

-- ---- Pijama Família (muitas variantes — todos tamanhos cobertos) ----

-- Produto 31: Família Ursinho — 8 variantes (bebê a adulto)
INSERT INTO pijamavariante (pijama_id, tamanho, cor_id, estoque, datacadastro, dataalteracao) VALUES
(31, 'RN',         7, 8,  NOW(), NOW()),
(31, 'P',          7, 10, NOW(), NOW()),
(31, 'M',          7, 12, NOW(), NOW()),
(31, 'QUATRO_ANOS',7, 9,  NOW(), NOW()),
(31, 'SEIS_ANOS',  7, 7,  NOW(), NOW()),
(31, 'OITO_ANOS',  7, 5,  NOW(), NOW()),
(31, 'G',          7, 3,  NOW(), NOW()),
(31, 'GG',         7, 1,  NOW(), NOW());

-- Produto 32: Família Estrelas — 9 variantes
INSERT INTO pijamavariante (pijama_id, tamanho, cor_id, estoque, datacadastro, dataalteracao) VALUES
(32, 'RN',           4, 6,  NOW(), NOW()),
(32, 'P',            4, 10, NOW(), NOW()),
(32, 'M',            4, 8,  NOW(), NOW()),
(32, 'DOIS_ANOS',    4, 12, NOW(), NOW()),
(32, 'QUATRO_ANOS',  4, 9,  NOW(), NOW()),
(32, 'SEIS_ANOS',    4, 7,  NOW(), NOW()),
(32, 'OITO_ANOS',    4, 5,  NOW(), NOW()),
(32, 'G',            4, 2,  NOW(), NOW()),
(32, 'GG',           4, 0,  NOW(), NOW());  -- estoque 0

-- Produto 33: Família Xadrez — 9 variantes
INSERT INTO pijamavariante (pijama_id, tamanho, cor_id, estoque, datacadastro, dataalteracao) VALUES
(33, 'RN',           8, 5,  NOW(), NOW()),
(33, 'P',            8, 8,  NOW(), NOW()),
(33, 'M',            8, 10, NOW(), NOW()),
(33, 'DOIS_ANOS',    8, 12, NOW(), NOW()),
(33, 'QUATRO_ANOS',  8, 9,  NOW(), NOW()),
(33, 'SEIS_ANOS',    8, 7,  NOW(), NOW()),
(33, 'DEZ_ANOS',     8, 4,  NOW(), NOW()),
(33, 'G',            8, 2,  NOW(), NOW()),
(33, 'GG',           8, 1,  NOW(), NOW());

-- ---- Coleção Natal (muitas variantes — tema natalino) ----

-- Produto 34: Natal Bebê — 3 variantes
INSERT INTO pijamavariante (pijama_id, tamanho, cor_id, estoque, datacadastro, dataalteracao) VALUES
(34, 'RN', 8,  10, NOW(), NOW()),
(34, 'P',  8,  8,  NOW(), NOW()),
(34, 'M',  8,  5,  NOW(), NOW());

-- Produto 35: Natal Infantil — 6 variantes
INSERT INTO pijamavariante (pijama_id, tamanho, cor_id, estoque, datacadastro, dataalteracao) VALUES
(35, 'UM_ANO',      8, 12, NOW(), NOW()),
(35, 'DOIS_ANOS',   8, 9,  NOW(), NOW()),
(35, 'QUATRO_ANOS', 8, 15, NOW(), NOW()),
(35, 'SEIS_ANOS',   8, 7,  NOW(), NOW()),
(35, 'OITO_ANOS',   8, 3,  NOW(), NOW()),
(35, 'DEZ_ANOS',    8, 1,  NOW(), NOW());

-- Produto 36: Natal Família — 10 variantes (maior do catálogo)
INSERT INTO pijamavariante (pijama_id, tamanho, cor_id, estoque, datacadastro, dataalteracao) VALUES
(36, 'RN',           8, 6,  NOW(), NOW()),
(36, 'P',            8, 9,  NOW(), NOW()),
(36, 'M',            8, 12, NOW(), NOW()),
(36, 'G',            8, 10, NOW(), NOW()),
(36, 'GG',           8, 7,  NOW(), NOW()),
(36, 'UM_ANO',       8, 14, NOW(), NOW()),
(36, 'DOIS_ANOS',    8, 11, NOW(), NOW()),
(36, 'QUATRO_ANOS',  8, 8,  NOW(), NOW()),
(36, 'SEIS_ANOS',    8, 5,  NOW(), NOW()),
(36, 'DEZ_ANOS',     8, 0,  NOW(), NOW());  -- estoque 0

-- ============================================================
-- PEDIDOS
-- status: 1=Aguardando Pagamento 2=Pago 3=Em Separação 4=Enviado 5=Entregue 6=Cancelado
-- forma_pagamento: 1=Pix 2=Boleto 3=Cartão Crédito 4=Cartão Débito
-- ============================================================

INSERT INTO pedido (id, data, total, valor_desconto, status, forma_pagamento, cliente_id, cupom_id,
  entrega_logradouro, entrega_numero, entrega_complemento, entrega_bairro, entrega_cep, entrega_municipio, entrega_estado, entrega_principal,
  datacadastro, dataalteracao) VALUES

-- Pedido 1: Entregue — Ana, Pix, com cupom BEMVINDO10
(1,  '2025-10-15 10:30:00', 134.82, 14.98, 5, 1, 1, 1,
  'Quadra 103 Sul Rua SE 03', '15', 'Apto 202', 'Plano Diretor Sul', '77020-014', 'Palmas', 'Tocantins', true,
  NOW(), NOW()),

-- Pedido 2: Enviado — Bruno, Cartão Crédito, sem cupom
(2,  '2025-11-20 14:15:00', 159.90, NULL, 4, 3, 2, NULL,
  'Rua das Flores', '200', NULL, 'Jardim Paulista', '01310-000', 'São Paulo', 'São Paulo', true,
  NOW(), NOW()),

-- Pedido 3: Em Separação — Carla, Pix, cupom NATAL15
(3,  '2025-12-01 09:00:00', 228.82, 40.40, 3, 1, 3, 2,
  'Av. Afonso Pena', '500', 'Sala 10', 'Centro', '30130-001', 'Belo Horizonte', 'Minas Gerais', true,
  NOW(), NOW()),

-- Pedido 4: Pago — Daniel, Boleto, cupom FAMILIA20
(4,  '2025-12-05 16:45:00', 239.84, 59.96, 2, 2, 4, 3,
  'Rua da Praia', '77', 'Casa', 'Copacabana', '22070-011', 'Rio de Janeiro', 'Rio de Janeiro', true,
  NOW(), NOW()),

-- Pedido 5: Aguardando Pagamento — Ana, Pix, sem cupom
(5,  '2025-12-10 11:20:00', 89.90, NULL, 1, 1, 1, NULL,
  'Quadra 103 Sul Rua SE 03', '15', 'Apto 202', 'Plano Diretor Sul', '77020-014', 'Palmas', 'Tocantins', true,
  NOW(), NOW()),

-- Pedido 6: Cancelado — Bruno, Débito, sem cupom
(6,  '2025-11-28 08:00:00', 79.90, NULL, 6, 4, 2, NULL,
  'Rua das Flores', '200', NULL, 'Jardim Paulista', '01310-000', 'São Paulo', 'São Paulo', true,
  NOW(), NOW());

-- ============================================================
-- ITENS DE PEDIDO
-- variante_id referencia pijamavariante.id
-- Os IDs de variante abaixo são baseados na ordem de inserção acima:
--   var 1-3 = produto 1 (RN,P,M) | var 4 = produto 4 (RN sem cor)
--   var 5-8 = produto 5 | var 9-12 = produto 6 | etc.
-- Usamos pijama_id + variante_id + preço coerente com a tabela pijama
-- ============================================================

-- Pedido 1 (total 134.82 com 10% desc de 14.98 → subtotal bruto 149.80)
INSERT INTO itempedido (pedido_id, pijama_id, variante_id, quantidade, preco_unitario, subtotal, datacadastro, dataalteracao) VALUES
(1, 11, (SELECT id FROM pijamavariante WHERE pijama_id=11 AND tamanho='QUATRO_ANOS' LIMIT 1), 1, 79.90, 79.90, NOW(), NOW()),
(1, 16, (SELECT id FROM pijamavariante WHERE pijama_id=16 AND tamanho='DOIS_ANOS'   LIMIT 1), 1, 74.90, 74.90, NOW(), NOW());

-- Pedido 2 (total 159.90, sem desconto)
INSERT INTO itempedido (pedido_id, pijama_id, variante_id, quantidade, preco_unitario, subtotal, datacadastro, dataalteracao) VALUES
(2, 32, (SELECT id FROM pijamavariante WHERE pijama_id=32 AND tamanho='G' LIMIT 1), 1, 159.90, 159.90, NOW(), NOW());

-- Pedido 3 (total 228.82 com 15% desc 40.40 → bruto 269.22 ≈ arredondado)
INSERT INTO itempedido (pedido_id, pijama_id, variante_id, quantidade, preco_unitario, subtotal, datacadastro, dataalteracao) VALUES
(3, 35, (SELECT id FROM pijamavariante WHERE pijama_id=35 AND tamanho='QUATRO_ANOS' LIMIT 1), 1, 89.90,  89.90,  NOW(), NOW()),
(3, 36, (SELECT id FROM pijamavariante WHERE pijama_id=36 AND tamanho='G'           LIMIT 1), 1, 149.90, 149.90, NOW(), NOW());

-- Pedido 4 (total 239.84 com 20% desc 59.96 → bruto 299.80)
INSERT INTO itempedido (pedido_id, pijama_id, variante_id, quantidade, preco_unitario, subtotal, datacadastro, dataalteracao) VALUES
(4, 31, (SELECT id FROM pijamavariante WHERE pijama_id=31 AND tamanho='M'  LIMIT 1), 1, 149.90, 149.90, NOW(), NOW()),
(4, 33, (SELECT id FROM pijamavariante WHERE pijama_id=33 AND tamanho='GG' LIMIT 1), 1, 149.90, 149.90, NOW(), NOW());

-- Pedido 5 (total 89.90, sem desconto)
INSERT INTO itempedido (pedido_id, pijama_id, variante_id, quantidade, preco_unitario, subtotal, datacadastro, dataalteracao) VALUES
(5, 35, (SELECT id FROM pijamavariante WHERE pijama_id=35 AND tamanho='SEIS_ANOS' LIMIT 1), 1, 89.90, 89.90, NOW(), NOW());

-- Pedido 6 (cancelado — 1 item)
INSERT INTO itempedido (pedido_id, pijama_id, variante_id, quantidade, preco_unitario, subtotal, datacadastro, dataalteracao) VALUES
(6, 12, (SELECT id FROM pijamavariante WHERE pijama_id=12 AND tamanho='SEIS_ANOS' LIMIT 1), 1, 74.90, 74.90, NOW(), NOW());

-- ============================================================
-- LISTA DE DESEJOS
-- ============================================================
INSERT INTO listadesejos (id, data_criacao, cliente_id, datacadastro, dataalteracao) VALUES
(1, '2025-10-01', 1, NOW(), NOW()),
(2, '2025-11-05', 2, NOW(), NOW()),
(3, '2025-12-01', 3, NOW(), NOW());

INSERT INTO lista_desejos_pijama (lista_desejos_id, pijama_id) VALUES
(1, 11),(1, 15),(1, 31),
(2, 22),(2, 27),(2, 36),
(3, 35),(3, 32),(3, 16);

