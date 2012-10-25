
--* Create a sample table

CREATE TABLE test_table (
	PFTimestamp CHAR(50),
	PFSymbol CHAR(10),
	PFprice FLOAT,
);
	
--* Create a fake stored procedure to call
CREATE ALIAS retrieve_and_mark_corp_intraday_pf_algo_orders_details FOR "com.aef.db.StoredProcedureStub.query";
--*CREATE ALIAS pfordtran.dbo.retrieve_and_mark_corp_intraday_pf_algo_orders_details @noMark=1 FOR "com.aef.db.StoredProcedureStub.query";

CREATE ALIAS update_ack FOR "com.aef.db.StoredProcedureStub.updateAck";

