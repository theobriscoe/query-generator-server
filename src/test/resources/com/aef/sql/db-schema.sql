
--* Create a sample table

CREATE TABLE test_table (
	PFBasketID CHAR(50),
	PFOrderID CHAR(50),
	symbol CHAR(10),
	orderqty int,
	buysell CHAR(4),
	settle_firm CHAR(10)
);
	
--* Create a fake stored procedure to call
CREATE ALIAS retrieve_and_mark_corp_intraday_pf_algo_orders_details FOR "com.aef.db.StoredProcedureStub.query";
--*CREATE ALIAS pfordtran.dbo.retrieve_and_mark_corp_intraday_pf_algo_orders_details @noMark=1 FOR "com.aef.db.StoredProcedureStub.query";

CREATE ALIAS update_ack FOR "com.aef.db.StoredProcedureStub.updateAck";

