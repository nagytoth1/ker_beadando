-- seed roles table
LOCK TABLES `roles` WRITE;
INSERT INTO `roles` (name) VALUES
('admin'),
('stacker'),
('user');
UNLOCK TABLES;

-- seed opsystems talbe
LOCK TABLES `systems` WRITE;
INSERT INTO `systems` (name) VALUES
('Windows 7'),
('Windows 8'),
('Windows 10'),
('FreeDOS'),
('Ubuntu Linux'),
('Kali Linux');
UNLOCK TABLES;

-- seed processors table
LOCK TABLES `cpus` WRITE;
INSERT INTO `cpus` (name, cores, cachemb, clockrate) VALUES
('Intel Core i7-9700KF',8,12,3.60),
('AMD Ryzen 5 5600G',6,16,3.90),
('AMD Ryzen 7 5800X',8,12,3.80),
('AMD Ryzen 3 1200',4,20,3.10),
('Intel Core i9-12900K',16,16,3.20);
UNLOCK TABLES;

-- seed psus table
LOCK TABLES `psus` WRITE;
INSERT INTO `psus` (type, performance, name) VALUES
('félmoduláris',400,'Akyga Basic AK-B1-400 OEM'),
('moduláris',850,'Corsair RM850x CP-9020180-EU'),
('félmoduláris',850,'FSP Hydro G Pro'),
('moduláris',650,'Zalman ZM650-GVII '),
('moduláris',500,'Gamdias HELIOS E1'),
('félmoduláris',600,'Be quiet! BN294 Pure Power 11');
UNLOCK TABLES;

-- seed rams table
LOCK TABLES `rams` WRITE;
INSERT INTO `rams` (name,type, speed, size) VALUES
('Kingston FURY Beast 16GB KVR32N11/16','DDR4',3200,16),
('Kingston ValueRAM KVR16N11/8','DDR4',2400,8),
('G.SKILL RipjawsX F3-12800CL10D','DDR3',1333,16),
('G.SKILL F3-1600C11S-8GNT','DDR3',1600,8),
('CSX CSXO-D2-LO-800','DDR2',800, 4),
('CSX CSXO-D2-LO-600','DDR2',600, 4);
UNLOCK TABLES;

-- seed storages table
LOCK TABLES `storages` WRITE;
INSERT INTO `storages` (name, port, capacitygb, size, cachemb, rpm) VALUES
('SeaGate ST2000DM008','SATA3',2048,3.5,256,7200),
('Toshiba P300 HDWD220UZSVA','SATA3',2048,3.5,128,5400),
('Western Digital Red WD20EFZX','SATA3',4096,3.5,64,6400),
('Seagate ST14000NM001G','SATA3',14436,3.5,218,0),
('Kingston A400 SA400S37','SATA3',240,2.5,64,0),
('Silicon Power A55', 'M.2',255,0,64,0);
UNLOCK TABLES;

-- seeds videocards table
LOCK TABLES `gpus` WRITE;
INSERT INTO `gpus` (name, clockrate, coolant) VALUES
('ASUS GeForce RTX 3060',1.75,'aktív'),
('Palit GeForce GTX 1050',1.66,'aktív'),
('GIGABYTE GeForce RTX 3050',1.82,'aktív'),
('ASUS GeForce GTX 1660 SUPER',1.86,'aktív'),
('Gainward GeForce GTX 1050',1.29,'aktív');
UNLOCK TABLES;

select * from storages;
select * from computers;
-- seed computers table
LOCK TABLES `computers` WRITE;
INSERT INTO `computers` (name, motherboard, cpu_id, ram_quantity, ram_id, gpu_id, psu_id, system_id) VALUES
('Pocok PC','Asus PRIME A320M-K',1,2,2,2,2,1),
('Gombóc PC','ASUS ROG STRIX Z690-A GAMING WIFI D4',2,2,3,3,3,4),
('Zebra PC', 'MSI MPG X570',3,4,2,3,3,2),
('Delfin PC','RX510ihih',4,2,2,3,3,4),
('Cica PC','ASUS TUF GAMING B550M-PLUS',2,2,1,5,4,3);
UNLOCK TABLES;
LOCK TABLES `computer_storage` WRITE;
INSERT INTO `computer_storage` (computer_id, storage_id) VALUES
(1,1),
(1,5),
(2,2),
(2,5),
(3,3),
(3,5);
UNLOCK TABLES;