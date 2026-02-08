INSERT INTO categoria(id, nome, descricao)
values
    (1,'COMERCIAL','Estabelecimentos comerciais'),
    (2,'INDUSTRIAL','Indústrias e fábricas'),
    (3,'PARTICULAR','Residências'),
    (4,'PUBLICO','Órgãos públicos')
ON CONFLICT (id) DO NOTHING;
