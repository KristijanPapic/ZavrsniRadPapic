using Carer.Common;
using Carer.Model.DomainModels;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Carer.Service
{
    public interface IPetService
    {
        Task DeletePetAsync(Guid id);
        Task<List<PetDomainModel>> GetAllPetAsync(PetFilter filter);
        Task PostPetAsync(PetDomainModel pet);
        Task PutPetAsync(PetDomainModel pet);
    }
}