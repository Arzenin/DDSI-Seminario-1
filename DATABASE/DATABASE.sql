CREATE TABLE Stock(
    Cproducto int,
    Cantidad int, 
    primary key(Cproducto));
    
CREATE TABLE Pedido(
    Cpedido int,
    Ccliente int,
    Fecha_pedido DATE,
    primary key(Cpedido)
    );
    
CREATE TABLE Detalle_Pedido(
    Cpedido int,
    Cproducto int,
    Cantidad int,
    PRIMARY KEY(Cpedido,Cproducto),
    FOREIGN KEY(Cproducto) REFERENCES Stock(Cproducto),
    FOREIGN KEY(Cpedido) REFERENCES Pedido(Cpedido)
);