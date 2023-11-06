DROP TABLE STOCK;
DROP TABLE PEDIDO;
DROP TABLE DETALLE_PEDIDO;

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

INSERT INTO Stock(Cproducto,Cantidad) VALUES(1,1);
INSERT INTO Pedido(Cpedido,Ccliente,Fecha_pedido) VALUES(1,1,TO_DATE('01/01/2002','dd/mm/yy')); 
INSERT INTO Detalle_Pedido(Cpedido,Cproducto,Cantidad) VALUES(1,1,1);  
SELECT * FROM Stock;
SELECT * FROM Pedido;
SELECT * FROM Detalle_Pedido;