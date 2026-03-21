insert into estado (nome, sigla, regiao) values ('Tocantins', 'TO', 2);
insert into estado (nome, sigla, regiao) values ('São Paulo', 'SP', 4);
insert into estado (nome, sigla, regiao) values ('Goiás', 'GO', 1);
insert into estado (nome, sigla, regiao) values ('Rio Grande do Sul', 'RS', 5);
insert into estado (nome, sigla, regiao) values ('Rio de Janeiro', 'RJ', 4);


insert into municipio (nome, id_estado) values ('Rio de Janeiro', 5);
insert into municipio (nome, id_estado) values ('São Paulo', 2);
insert into municipio (nome, id_estado) values ('Palmas', 1);
insert into municipio (nome, id_estado) values ('Gurupi', 1);

-- Alunos
INSERT INTO aluno (nome, sobrenome, data_nascimento, cpf, email)
VALUES ('Carlos Henrique', 'Tolentino', '2000-05-10', '12345678901', 'carlos.henrique@email.com');

INSERT INTO aluno (nome, sobrenome, data_nascimento, cpf, email)
VALUES ('Mariana', 'Souza', '1999-11-20', '98765432100', 'mariana.souza@email.com');

INSERT INTO aluno (nome, sobrenome, data_nascimento, cpf, email)
VALUES ('João', 'Pereira', '2001-02-15', '45678912345', 'joao.pereira@email.com');

INSERT INTO aluno (nome, sobrenome, data_nascimento, cpf, email)
VALUES ('Fernanda', 'Oliveira', '1998-08-30', '32165498700', 'fernanda.oliveira@email.com');

INSERT INTO aluno (nome, sobrenome, data_nascimento, cpf, email)
VALUES ('Leandra', 'Silva', '2002-01-05', '15975345682', 'leandra.silva@email.com');

-- Telefones
INSERT INTO aluno_telefone (id_aluno, codigo_area, numero) VALUES (1, '63', '999999999');
INSERT INTO aluno_telefone (id_aluno, codigo_area, numero) VALUES (1, '61', '988888888');
INSERT INTO aluno_telefone (id_aluno, codigo_area, numero) VALUES (2, '62', '977777777');
INSERT INTO aluno_telefone (id_aluno, codigo_area, numero) VALUES (3, '63', '966666666');
INSERT INTO aluno_telefone (id_aluno, codigo_area, numero) VALUES (4, '11', '955555555');
INSERT INTO aluno_telefone (id_aluno, codigo_area, numero) VALUES (5, '21', '944444444');
INSERT INTO aluno_telefone (id_aluno, codigo_area, numero) VALUES (5, '63', '933333333');

