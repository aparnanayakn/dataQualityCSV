package dataQualityCheck;

import java.util.Iterator;
import java.util.List;

import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.reasoner.ValidityReport;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.riot.RDFDataMgr;

public class dqRootCause {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Model data = RDFDataMgr.loadModel("data.ttl");
		Model schema = RDFDataMgr.loadModel("csvonto.ttl");

		//Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
				
	    List rules = Rule.rulesFromURL("rules.n3");

		GenericRuleReasoner reasoner = new GenericRuleReasoner(rules);
		reasoner.bindSchema(schema);
		//reasoner.setOWLTranslation(true);               // not needed in RDFS case
		//reasoner.setTransitiveClosureCaching(true);

	//	Reasoner reasoner = new GenericRuleReasoner( Rule.rulesFromURL( "rules.n3" ) );
		
		InfModel infModel = ModelFactory.createInfModel( reasoner, data ); 
		
		Model deductions = infModel.getDeductionsModel();
		deductions.write(System.out, "N3"); 
		
	/*	StmtIterator it = infModel.listStatements();
		
		while ( it.hasNext() )
		{
			Statement stmt = it.nextStatement();
			
			Resource subject = stmt.getSubject();
			Property predicate = stmt.getPredicate();
			RDFNode object = stmt.getObject(); 
			System.out.println( subject.toString() + " " + predicate.toString() + " " + object.toString() );
	}
		/*
	
		Model deductions = infmodel.getDeductionsModel();
		deductions.write(System.out, "N3"); 
	/*	ValidityReport validity = infModel.validate();
		if (validity.isValid()) {
		    System.out.println("OK");
		} else {
		    System.out.println("Conflicts");
		    for (Iterator i = validity.getReports(); i.hasNext(); ) {
		        System.out.println(" - " + i.next());
		    }
		} 
*/
	}
}
