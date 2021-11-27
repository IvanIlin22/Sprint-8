create table orders (
id varchar(255) not null,
address varchar(255),
recipient varchar(255),
item_id bigint,
primary key (id),
FOREIGN KEY (item_id)  REFERENCES item (id));

INSERT INTO item VALUES(1, 'Some item1'),
                       (2, 'Some item2'),
                       (3,  'Some item3');

INSERT INTO orders VALUES(1, 'Some address1', 'some recipient1'),
                         (2, 'Some address2', 'some recipient2'),
                         (3, 'Some address3', 'some recipient3');

INSERT INTO order_info VALUES(1, 'signature1', 'CREATED'),
                         (2, 'signature2', 'CREATED'),
                         (3, 'signature3', 'CREATED');
