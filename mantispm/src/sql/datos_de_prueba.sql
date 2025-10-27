-- =====================================
-- Inserccion de Datos de ejemplo
-- =====================================
USE mantis_pm;

-- Empresas / plantas / equipos
INSERT INTO empresa (nombre, cuit, direccion) VALUES ('El Pampeano SRL','20-12345678-9','Ruta 3 km 12');
INSERT INTO planta (empresa_id, nombre, ubicacion) VALUES (1,'Planta Principal','Cutral Co, Neuquén');
INSERT INTO equipo (planta_id, nombre, codigo_equipamiento, tipo) VALUES (1,'Prensa 1','PR-001','Prensa Hidráulica');

-- Sensores
INSERT INTO sensor (equipo_id, uid_sensor, tipo_sensor, descripcion, registrado_en)
VALUES (1,'sensor-vib-0001','VIBRACION','Sensor de vibración eje principal',NOW());

-- Roles y usuario demo
INSERT INTO rol (nombre, descripcion) VALUES ('ADMIN','Administrador del sistema'),('TECNICO','Técnico de mantenimiento');
-- La password_hash es de ejemplo; en entrega académica puede dejarse como placeholder
INSERT INTO usuario (nombre,email,password_hash,rol_id) VALUES ('Juana','juana@elpampeano.local','$2y$EXAMPLEHASH',1);

-- Ejemplo de INSERT de lectura (idempotente usando lectura_uid)
INSERT INTO lectura_sensor (lectura_uid, sensor_id, valor, unidad, registrado_en, payload_raw)
VALUES ('rw-20251005-0001', 1, 3.45, 'mm/s', '2025-10-05 16:30:00', JSON_OBJECT('sample_rate',100,'gateway','gw-01'))
ON DUPLICATE KEY UPDATE
  valor = VALUES(valor),
  ingestion_ts = CURRENT_TIMESTAMP,
  payload_raw = JSON_MERGE_PATCH(payload_raw, VALUES(payload_raw));

-- Generar alerta a partir de la lectura (ejemplo)
-- Usamos la última lectura insertada (se asume que LAST_INSERT_ID() devolvió la lectura)
SET @last_lectura_id = (SELECT lectura_id FROM lectura_sensor WHERE lectura_uid = 'rw-20251005-0001' LIMIT 1);
INSERT INTO alerta (lectura_id, sensor_id, equipo_id, tipo_alerta, severidad, mensaje)
VALUES (@last_lectura_id, 1, 1, 'VIBRACION_ALTA','ALTA','Vibración por encima del umbral configurado');

-- Crear OT derivada
SET @last_alerta_id = (SELECT alerta_id FROM alerta WHERE lectura_id = @last_lectura_id LIMIT 1);
INSERT INTO orden_trabajo (alerta_id, equipo_id, prioridad, descripcion)
VALUES (@last_alerta_id, 1, 'ALTA', 'Revisión urgente eje principal por vibración alta');

-- Ejemplo de intervención
SET @last_ot_id = (SELECT ot_id FROM orden_trabajo WHERE alerta_id = @last_alerta_id LIMIT 1);
INSERT INTO intervencion (ot_id, usuario_id, observaciones)
VALUES (@last_ot_id, 1, 'Diagnóstico inicial: se detectó desbalance. Requiere alineación');
