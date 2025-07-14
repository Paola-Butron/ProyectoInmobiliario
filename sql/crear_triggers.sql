-- Trigger 1: Validar que el precio del inmueble sea mayor a cero
CREATE OR REPLACE TRIGGER trg_validar_precio_inmueble
BEFORE INSERT OR UPDATE ON INMUEBLE
FOR EACH ROW
BEGIN
    IF :NEW.PRECIO <= 0 THEN
        RAISE_APPLICATION_ERROR(-20010, '❌ El precio del inmueble debe ser mayor a cero.');
    END IF;
END;
/

-- Trigger 2: Evitar que una venta CANCELADA tenga un monto mayor a cero
CREATE OR REPLACE TRIGGER trg_venta_cancelada_sin_monto
BEFORE INSERT OR UPDATE ON VENTA
FOR EACH ROW
BEGIN
    IF UPPER(:NEW.estadoVenta) = 'CANCELADA' AND :NEW.montoTotal > 0 THEN
        RAISE_APPLICATION_ERROR(-20023, '❌ Una venta cancelada no debe tener monto mayor a cero.');
    END IF;
END;
/
