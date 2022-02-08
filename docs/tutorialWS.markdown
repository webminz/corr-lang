---
layout: page
title: "Tutorial: Web Service Federation"
permalink: tutorialWS/
nav_order: 4
---

# Tutorial: Web Service Federation

In this tutorial, you will learn how to easily implement web service federation with CorrLang.

## The Prospect

Let us assume the following example scenario:

There is a hypothetical retails company running three systems:
- One for storing the purchases of customers, called `Sales`.
- One for storing the invoices that were send to customers, called `Invoices`.
- One for storing the employee data.

These systems have grown historically, were developed in different points in time by differnt teams. However, they sometimes share the same information, e.g. customers naturally appear in both `Sales` and `Invoices` (they are called "clients" there), and it is, of course, possible that an "employee" is also a "customer.

Now, the retail company is looking for a report of the data in all the systems, where the shared information is already aligned.

In this tutorial, I will demonstrate how _CorrLang_ can help with this.


## Preliminaries: Getting the Demo Code

To follow along with the example, it is advised to check out the demo code:
```
https://github.com/webminz/usecase_SalesInvicesHR.git
```

Follow the README instructions in that repository to set up the three Node.js endpoints.

## Exploring the scenario

Assuming, the three endpoints are up and running and accessible at
- http://localhost:4011
- http://localhost:4012
- http://localhost:4013

The first step when applying _CorrLang_ is always the creation of a so-called _CorrSpec.
Thus, it is time to open you favourite text editor and create a new empty file called `spec.corr` (actually the name nor the ending of this file does not really matter).

First, you will have to specify the endpoints we are building our correspondence on:
```
endpoint Sales {
    type SERVER
    at http://localhost:4011
    technology GRAPH_QL
}

endpoint Invoices {
    type SERVER
    at http://localhost:4012
    technology GRAPH_QL
}

endpoint HR {
    type SERVER
    at http://localhost:4013
    technology GRAPH_QL
}
```

These definitions should be readable pretty intuitively.
In layer articles, we will explain the meaning of these keywords in greater detail.

The _core_ of every _CorrSpec_ is the `correspondence`.
The following line defines a correspondence between the three endpoints and give it the name "Backoffice".
```
correspondence Backoffice (Sales, Invoices, HR) {

}
```
Initially, it is empty, which means that we do not specify any relationships yet.

Finally, in order to actually _run_ CorrLang, we need to define a goal.
```
goal Draw {
    correspondence Backoffice
    action SCHEMA
    technology IMAGE
    target FILE {
        at file:./vizz.png
    }
}
```
This goal will create the schema of the resulting "Backoffice" system in the form of a picture (i.e. as a PNG image).

Hence, it is time to run corrlang for the first time! Open a terminal, navigate to the folder where `spec.corr` is stored, and run the following command (assuming that you had put the corrlang.sh/corrlang.bat on your $PATH during the installation).
```
corrlang spec.corr g:Draw
```

When CorrLang terminates, you should see a new file `vizz.png` in the working directory.
Open that file in you favourite picture viewer and try to intepret the result considering the fact the the `correspondence` is empty.


Well, an empty `correspondence` means that there are no further constraints on how the systems should be aligned.
Thus, the systems are simply put in _parallel composition".


You can even experience this as a running system!
Try adding the following goal to `spec.corr`.

```
goal Federation {
    correspondence Backoffice
    action FEDERATION
    technology GRAPH_QL
    target SERVER {
    		contextPath graphql/
    		port 8081
    }
}
```
and run CorrLang with
```
corrlang spec.corr g:Federation
```

Try now opening the endpoint (http://localhost:8081/graphql) in a GraphQL client.
Can you see what happens when two systems are using the same name (i.e. name clashes)?


## Aligning your first data type

Clearly, the three schemas share a common entity: 
They are called `Customer` in `Sales`, `Client` in `Invoices`, and `Employee` in `HR`.
Hence, we want to unify them under the same concept, and we call it `Partner`.

To do this, let us add our first _identification-commonalitu_:
```
correspondence Backoffice (Sales, Invoices, HR) {

    identify (Sales.Customer, Invoices.Client, HR.Employee) as Partner;
}
```
Now, try calling the `Draw` goal and see what happens.

It turns out, that these three types have several _Attributes_ that are shared as well.
Moreover, the `Address` type is shared among `Sales` and `Invoices`.
Hence, we are extending our correspondence as follows:
```
correspondence Backoffice (Sales, Invoices, HR) {

    identify (Sales.Customer,Invoices.Client,HR.Employee) as Partner
         with {
             identify (Sales.Customer.id,Invoices.Client.id,HR.Employee.id) as id;
             identify (Sales.Customer.name,Invoices.Client.name) as name;
             identify (Sales.Customer.email,HR.Employee.email) as email;
             identify (Sales.Customer.address,Invoices.Client.address) as address;
         };
    identify (Sales.Address,Invoices.Address) as Address
            with {
                identify (Sales.Address.street,Invoices.Address.street) as street;
                identify (Sales.Address.city,Invoices.Address.city) as city;
                identify (Sales.Address.postalCode,Invoices.Address.postalCode) as postalCode;
                identify (Sales.Address.country,Invoices.Address.country) as country;
                identify (Sales.Address.state,Invoices.Address.state) as state;
        };
}
```

Run the `Draw` goal again and look at the result.

Now, that these data types are so nicely aligned, it would be nice to access the respective data all at once.
To do this, we have to align respective _query operations_ as well.
Add the following line into the top level of the correspondence.
```
    identify (Sales.Query.customers,Invoices.Query.clients,HR.Query.employees) as Query.partners;
```
OK! It is time to see that in action. Run the `Federation` goal again!
and query the Partner-data by sending the following query:
```
query {
    partners {
        fullName
        purchases {
            id
        }
        invoices {
            id
        }
        worksAt {
            name
        }
    }
}
```
Look carefully at the result and try to interpret it

## Aligning data

(coming soon)

## Bonus: Video

The following video demonstrates some of the highlights in the description above.


<iframe width="560" height="315" src="https://www.youtube.com/embed/98z64J3mPiQ" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>


